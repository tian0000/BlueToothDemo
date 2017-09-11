package com.example.tian.bluetoothdemo;

/**
 * @name yanantian
 * @motto 莫羡他人谢语花, 腹有诗书气自华
 * @E-mail 1173568715@qq.com
 * @WX 15978622391
 */

public class Bean {
    String name;
    String content;

    public Bean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Bean() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
