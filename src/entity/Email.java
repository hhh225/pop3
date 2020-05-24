package entity;

public class Email {
    String mail_id;
    String user_id;
    String user_from;
    String user_to;
    String mail_subject;
    String content;
    String mail_date;
    String MIME_version;
    String content_type;
    String content_transfer_conding;
    String sendate;
    String message_id;
    String flag;

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_from() {
        return user_from;
    }

    public void setUser_from(String user_from) {
        this.user_from = user_from;
    }

    public String getUser_to() {
        return user_to;
    }

    public void setUser_to(String user_to) {
        this.user_to = user_to;
    }

    public String getMail_subject() {
        return mail_subject;
    }

    public void setMail_subject(String mail_subject) {
        this.mail_subject = mail_subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMail_date() {
        return mail_date;
    }

    public void setMail_date(String mail_data) {
        this.mail_date = mail_data;
    }

    public String getMIME_version() {
        return MIME_version;
    }

    public void setMIME_version(String MIME_version) {
        this.MIME_version = MIME_version;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getContent_transfer_conding() {
        return content_transfer_conding;
    }

    public void setContent_transfer_conding(String content_transfer_conding) {
        this.content_transfer_conding = content_transfer_conding;
    }

    public String getSendate() {
        return sendate;
    }

    public void setSendate(String sendate) {
        this.sendate = sendate;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
