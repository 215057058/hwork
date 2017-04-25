package com.hwork.frame.mybatis.cfg;

/**
 * Created by lipz on 2017/4/25.
 */
public class DataSourceInfo {
    private String dsId;
    private String fdsCode;
    private String dsUrl;
    private String dsDriverName;
    private String username;
    private String password;
    private String runType;
    public String getDsId() {
        return dsId;
    }
    public void setDsId(String dsId) {
        this.dsId = dsId;
    }
    public String getFdsCode() {
        return fdsCode;
    }
    public void setFdsCode(String fdsCode) {
        this.fdsCode = fdsCode;
    }
    public String getDsUrl() {
        return dsUrl;
    }
    public void setDsUrl(String dsUrl) {
        this.dsUrl = dsUrl;
    }
    public String getDsDriverName() {
        return dsDriverName;
    }
    public void setDsDriverName(String dsDriverName) {
        this.dsDriverName = dsDriverName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRunType() {
        return runType;
    }
    public void setRunType(String runType) {
        this.runType = runType;
    }
    public DataSourceInfo(String dsId, String fdsCode, String dsUrl, String dsDriverName, String username,
                          String password, String runType) {
        super();
        this.dsId = dsId;
        this.fdsCode = fdsCode;
        this.dsUrl = dsUrl;
        this.dsDriverName = dsDriverName;
        this.username = username;
        this.password = password;
        this.runType = runType;
    }
    public DataSourceInfo() {
        super();
        // TODO Auto-generated constructor stub
    }




}
