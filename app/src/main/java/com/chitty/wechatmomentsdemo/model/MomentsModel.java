package com.chitty.wechatmomentsdemo.model;

import java.util.List;

/**
 * Created by Chitty on 18/4/11.
 */

public class MomentsModel {

    private String profileimage;// TODO "profile-image"
    private String avatar;
    private String nick;
    private String username;

    private String content;
    private SenderBean sender;
    private String error;
    private List<ImagesBean> images;
    private String unknown_error;// TODO "unknown error"
    private List<CommentsBean> comments;

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public String getUnknown_error() {
        return unknown_error;
    }

    public void setUnknown_error(String unknown_error) {
        this.unknown_error = unknown_error;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class SenderBean {
        /**
         * username : jport
         * nick : Joe Portman
         * avatar : https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w
         */

        private String username;
        private String nick;
        private String avatar;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return "SenderBean{" +
                    "username='" + username + '\'' +
                    ", nick='" + nick + '\'' +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }

    public static class ImagesBean {
        /**
         * url : https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "ImagesBean{" +
                    "url='" + url + '\'' +
                    '}';
        }
    }

    public static class CommentsBean {
        /**
         * content : Good.
         * sender : {"username":"outman","nick":"Super hero","avatar":"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w"}
         */

        private String content;
        private SenderBeanX sender;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public SenderBeanX getSender() {
            return sender;
        }

        public void setSender(SenderBeanX sender) {
            this.sender = sender;
        }

        public static class SenderBeanX {
            /**
             * username : outman
             * nick : Super hero
             * avatar : https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w
             */

            private String username;
            private String nick;
            private String avatar;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            @Override
            public String toString() {
                return "SenderBeanX{" +
                        "username='" + username + '\'' +
                        ", nick='" + nick + '\'' +
                        ", avatar='" + avatar + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "CommentsBean{" +
                    "content='" + content + '\'' +
                    ", sender=" + sender +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MomentsModel{" +
                "profileimage='" + profileimage + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nick='" + nick + '\'' +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", sender=" + sender +
                ", error='" + error + '\'' +
                ", images=" + images +
                ", unknown_error='" + unknown_error + '\'' +
                ", comments=" + comments +
                '}';
    }
}
