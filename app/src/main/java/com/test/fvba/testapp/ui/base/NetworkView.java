package com.test.fvba.testapp.ui.base;


/**
 * Handles network callback according to response
 */
public interface NetworkView extends MVPView {

    /**
     * Generic errors
     */
    void onGenericError();

    void onNoConnection();
}