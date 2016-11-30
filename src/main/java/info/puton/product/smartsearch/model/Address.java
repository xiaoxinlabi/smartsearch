package info.puton.product.smartsearch.model;

/**
 * Created by taoyang on 2016/10/11.
 */
public class Address extends BaseSearchResult {

    private String accountId; //账号

    private String englishName; //英文名

    private String chineseName; //中文名

    private String fixedPhone;  //座机

    private String mobilePhone; //手机

    private String fax; //传真

    private String email; //邮箱

    private String address; //地址

    private String qq; //扣扣

    private String organization; //机构

    private String department; //部门

    private String position; //职位

    private String remark; //备注

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "accountId='" + accountId + '\'' +
                ", englishName='" + englishName + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", fixedPhone='" + fixedPhone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", qq='" + qq + '\'' +
                ", organization='" + organization + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
