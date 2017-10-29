package com.vint.timeapp.presenter;

import com.vint.timeapp.view.BaseView;

public interface Presenter<V extends BaseView> {

    void bind(final V view);

    void unbind();

}
