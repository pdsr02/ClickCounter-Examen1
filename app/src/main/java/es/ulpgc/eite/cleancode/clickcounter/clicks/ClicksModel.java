package es.ulpgc.eite.cleancode.clickcounter.clicks;

import android.util.Log;

public class ClicksModel implements ClicksContract.Model {

  public static String TAG = ClicksModel.class.getSimpleName();

  private int clicks;

  public ClicksModel() {
    clicks=0;
  }

  @Override
  public int getStoredData() {
    // Log.e(TAG, "getStoredData()");
    return clicks;
  }

  @Override
  public void onRestartScreen() {
    // Log.e(TAG, "onRestartScreen()");
    clicks=0;
  }

  @Override
  public void restoredCounterState(int clicks){
    this.clicks=clicks;
  }


}
