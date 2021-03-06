package br.com.rafael.seriespopulares.test.common;

import android.content.Context;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import br.com.rafael.seriespopulares.SeriesPopularesApplication;
import br.com.rafael.seriespopulares.data.DataManager;
import br.com.rafael.seriespopulares.test.common.injection.component.TestComponent;

/**
 * Created by rafael on 9/26/16.
 **/

public class TestComponentRule implements TestRule {

    private TestComponent mTestComponent;
    private Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public DataManager getMockDataManager() {
        return mTestComponent.dataManager();
    }

    public void setupDaggerTestComponentInApplication() {
        SeriesPopularesApplication application = SeriesPopularesApplication.get(mContext);

        application.setComponent(mTestComponent);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    mTestComponent = null;
                }
            }
        };
    }
}
