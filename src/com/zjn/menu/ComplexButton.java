package com.zjn.menu;

/*
* ����: ComplexButton </br>
* ������ com.souvc.weixin.menu
* ����: ���˵��� :�����ж����˵����һ���˵�������˵�������ж������ԣ�name��sub_button����sub_button����һ���Ӳ˵������� </br>
 */

public class ComplexButton extends Button {
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
