package com.vint.timeapp.presenter;

import com.vint.timeapp.view.BaseView;

public class BasePresenter<V extends BaseView> implements Presenter<V> {

    private V mView;

    @Override
    public void bind(final V view) {
        mView = view;
    }

    @Override
    public void unbind() {
        mView = null;
    }

    protected final V getView() {
        return mView;
    }
}
