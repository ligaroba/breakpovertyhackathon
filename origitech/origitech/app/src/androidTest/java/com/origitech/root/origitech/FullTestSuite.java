package com.origitech.root.origitech;

import android.test.AndroidTestCase;
import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;

/**
 * Created by root on 10/17/15.
 */
public class FullTestSuite extends AndroidTestCase  {

    public static Test Suite(){
        return new TestSuiteBuilder(FullTestSuite.class).includeAllPackagesUnderHere().build();

    }
    public FullTestSuite(){
        super();
    }
}
