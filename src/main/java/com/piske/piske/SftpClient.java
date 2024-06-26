package com.piske.piske;

import com.jcraft.jsch.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * A simple SFTP client using JSCH http://www.jcraft.com/jsch/
 */
public final class SftpClient {
    private final String host;
    private final int port;
    private final String username;
    private final JSch jsch;
    private ChannelSftp channel;
    private Session session;

    /**
     * @param host     remote host
     * @param port     remote port
     * @param username remote username
     */
    public SftpClient(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
        jsch = new JSch();
    }

    /**
     * Use default port 22
     *
     * @param host     remote host
     * @param username username on host
     */
    public SftpClient(String host, String username) {
        this(host, 22, username);
    }

    /**
     * Authenticate with remote using password
     *
     * @param password password of remote
     * @throws JSchException If there is problem with credentials or connection
     */
    public void authPassword(String password) throws JSchException {
        session = jsch.getSession(username, host, port);
        // disable known hosts checking
        // if you want to set knows hosts file You can set with jsch.setKnownHosts("path
        // to known hosts file");
        var config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setPassword(password);
        session.connect();
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
    }

    public void authKey(String keyPath, String pass) throws JSchException {
        jsch.addIdentity(keyPath, pass);
        session = jsch.getSession(username, host, port);
        // disable known hosts checking
        // if you want to set knows hosts file You can set with jsch.setKnownHosts("path
        // to known hosts file");
        var config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
    }

    /**
     * List all files including directories
     *
     * @param remoteDir Directory on remote from which files will be listed
     * @throws SftpException If there is any problem with listing files related to
     *                       permissions etc
     * @throws JSchException If there is any problem with connection
     */
    @SuppressWarnings("unchecked")
    public List<String> listFiles(String remoteDir) throws SftpException, JSchException {
        List<String> filenames = new ArrayList<String>();
        if (channel == null) {
            throw new IllegalArgumentException("Connection is not available");
        }
        System.out.printf("Listing [%s]...%n", remoteDir);
        channel.cd(remoteDir);
        Vector<ChannelSftp.LsEntry> files = channel.ls(".");
        for (ChannelSftp.LsEntry file : files) {
            var name = file.getFilename();
            var attrs = file.getAttrs();
            var permissions = attrs.getPermissionsString();
            var size = humanReadableByteCount(attrs.getSize());
            if (attrs.isDir()) {
                size = "PRE";
            }
            filenames.add(name);
            System.out.printf("[%s] %s(%s)%n", permissions, name, size);

        }
        return filenames;
    }

    private String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit)
            return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        var pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    /**
     * Upload a file to remote
     *
     * @param localPath  full path of location file
     * @param remotePath full path of remote file
     * @throws JSchException If there is any problem with connection
     * @throws SftpException If there is any problem with uploading file permissions
     *                       etc
     */
    public void uploadFile(String localPath, String remotePath) throws JSchException, SftpException {
        System.out.printf("Uploading [%s] to [%s]...%n", localPath, remotePath);
        if (channel == null) {
            throw new IllegalArgumentException("Connection is not available");
        }
        channel.put(localPath, remotePath);
    }

    /**
     * Download a file from remote
     *
     * @param remotePath full path of remote file
     * @throws SftpException If there is any problem with downloading file related
     *                       permissions etc
     */
    public String downloadFile(String remotePath) throws SftpException {
        if (channel == null) {
            throw new IllegalArgumentException("Connection is not available");
        }
        File file = new File("map.json");
        channel.get(remotePath, file.getAbsolutePath());
        String json = null;
        try {
            json = Files.readString(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("test" + json);
        return json;
    }

    /**
     * Delete a file on remote
     *
     * @param remoteFile full path of remote file
     * @throws SftpException If there is any problem with deleting file related to
     *                       permissions etc
     */
    public void delete(String remoteFile) throws SftpException {
        System.out.printf("Deleting [%s]...%n", remoteFile);
        if (channel == null) {
            throw new IllegalArgumentException("Connection is not available");
        }
        channel.rm(remoteFile);
    }

    /**
     * Disconnect from remote
     */
    public void close() {
        if (channel != null) {
            channel.exit();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
