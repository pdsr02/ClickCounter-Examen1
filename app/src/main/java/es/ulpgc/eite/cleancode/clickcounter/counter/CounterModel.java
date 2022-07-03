package es.ulpgc.eite.cleancode.clickcounter.counter;

import android.util.Log;

public class CounterModel implements CounterContract.Model {

  public static String TAG = CounterModel.class.getSimpleName();

  private int counter;
  private int clicks;

  public CounterModel() {
    counter = 0;
  }

  @Override
  public int getStoredData() {
    // Log.e(TAG, "getStoredData()");
    return counter;
  }

  @Override
  public int getNumClicks() {
    // Log.e(TAG, "getStoredData()");
    return clicks;
  }

  @Override
  public void restartData(){
    counter=0;
  }

  @Override
  public void incrementCounter(){
    counter++;
    clicks++;
  }

  @Override
  public void restoredClicks(boolean clear){
      clicks=0;
  }
}
