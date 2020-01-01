package com.example.schoolapp;

public class ListWord{
        private String full_name;//stu
        private String email;//stu
        private String id;//stu
        private Long num_absent;//stu
        private String phone;//stu
        private boolean isChecked;//stu

        /*************************************************************/
        private String TeacherFull_name;
        private String TeacherEmail;
        private String TeacherPhone;
        private String TeacherId;
        private String TeacherPassword;
        private String TeacherUsername;

        /********************/
        private String TableClassSubjectTeacherName;
        private String TableClassSubjectTeacherSubject;
        private String TableClassSubjectTeacherDate;
        private String TableClassSubjectTeacheTime;

        public ListWord(){

        }

        public ListWord(String TableClassSubjectTeacherName, String TableClassSubjectTeacherSubject, String TableClassSubjectTeacherDate,String TableClassSubjectTeacheTime){
            this.TableClassSubjectTeacherName=TableClassSubjectTeacherName;
            this.TableClassSubjectTeacherSubject=TableClassSubjectTeacherSubject;
            this.TableClassSubjectTeacherDate=TableClassSubjectTeacherDate;
            this.TableClassSubjectTeacheTime=TableClassSubjectTeacheTime;
        }

        public ListWord(String TeacherFull_name,String TeacherEmail){
            this.TeacherFull_name=TeacherFull_name;
            this.TeacherEmail=TeacherEmail;
        }
        public ListWord(String TeacherFull_name,String TeacherEmail,String TeacherPhone,String TeacherId,String TeacherPassword,String TeacherUsername){
            this.TeacherFull_name=TeacherFull_name;
            this.TeacherEmail=TeacherEmail;
            this.TeacherPhone=TeacherPhone;
            this.TeacherId=TeacherId;
            this.TeacherPassword=TeacherPassword;
            this.TeacherUsername=TeacherUsername;
        }
        public ListWord(String full_name) {
            this.full_name = full_name;
        }

        public ListWord(String email,String full_name,String id,Long num_absent,String phone) {
            this.full_name = full_name;
            this.email=email;
            this.id=id;
            this.num_absent=num_absent;
            this.phone=phone;
            isChecked=false;
        }

    public String getTableClassSubjectTeacherDate() {
        return TableClassSubjectTeacherDate;
    }

    public String getTableClassSubjectTeacherName() {
        return TableClassSubjectTeacherName;
    }

    public String getTableClassSubjectTeacherSubject() {
        return TableClassSubjectTeacherSubject;
    }

    public String getTableClassSubjectTeacheTime() {
        return TableClassSubjectTeacheTime;
    }

    public void setTableClassSubjectTeacherDate(String tableClassSubjectTeacherDate) {
        TableClassSubjectTeacherDate = tableClassSubjectTeacherDate;
    }

    public void setTableClassSubjectTeacherName(String tableClassSubjectTeacherName) {
        TableClassSubjectTeacherName = tableClassSubjectTeacherName;
    }

    public void setTableClassSubjectTeacherSubject(String tableClassSubjectTeacherSubject) {
        TableClassSubjectTeacherSubject = tableClassSubjectTeacherSubject;
    }

    public void setTableClassSubjectTeacheTime(String tableClassSubjectTeacheTime) {
        TableClassSubjectTeacheTime = tableClassSubjectTeacheTime;
    }

    public void setTeacherEmail(String teacherEmail) {
        TeacherEmail = teacherEmail;
    }

    public void setTeacherFull_name(String teacherFull_name) {
        TeacherFull_name = teacherFull_name;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }

    public void setTeacherPassword(String teacherPassword) {
        TeacherPassword = teacherPassword;
    }

    public void setTeacherPhone(String teacherPhone) {
        TeacherPhone = teacherPhone;
    }

    public void setTeacherUsername(String teacherUsername) {
        TeacherUsername = teacherUsername;
    }


    public String getTeacherUsername() {
        return TeacherUsername;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean ischecked) {
        this.isChecked = ischecked;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public String getTeacherPassword() {
        return TeacherPassword;
    }

    public String getTeacherPhone() {
        return TeacherPhone;
    }

    public String getTeacherEmail() {
        return TeacherEmail;
    }

    public String getTeacherFull_name() {
        return TeacherFull_name;
    }

    public void setEmail(String email) {
            this.email = email;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setNum_absent(Long num_absent) {
            this.num_absent = num_absent;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFull_name() {
            return full_name;
        }


        public String getEmail() {
            return email;
        }

        public String getId() {
            return id;
        }

        public String getPhone() {
            return phone;
        }

        public Long getNum_absent() {
            return num_absent;
        }
}