package es.ulpgc.eite.cleancode.clickcounter.clicks;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.clickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.clickcounter.app.ClicksToCounterState;
import es.ulpgc.eite.cleancode.clickcounter.app.CounterToClicksState;

public class ClicksPresenter implements ClicksContract.Presenter {

  public static String TAG = ClicksPresenter.class.getSimpleName();

  private WeakReference<ClicksContract.View> view;
  private ClicksState state;
  private ClicksContract.Model model;
  private AppMediator mediator;

  public ClicksPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getClicksState();
  }

  @Override
  public void onStart() {
    Log.e(TAG, "onStart()");

    // initialize the state if is necessary
    if (state == null) {
      state = new ClicksState();
    }

    // use passed state if is necessary
    CounterToClicksState savedState = getStateFromPreviousScreen();
    if (savedState != null) {
      /*En este caso podemos hacer dos cosas:
      1) Guardar el estado pasado de la otra pantalla en el modelo
      y recuperarlo despues para actualizar el estado*/
     // model.restoredCounterState(savedState.clicks);
     // state.clicks = model.getStoredData();

      /*2)No guardar el estado pasado de la otra pantalla en el modelo
      sino actualizar directamente el estado*/
      //caso particular del 1
      state.clicks = savedState.clicks;

    }

  }

  @Override
  public void onRestart() {
    Log.e(TAG, "onRestart()");
    // update the model if is necessary
  }


  @Override
  public void onResume() {
    Log.e(TAG, "onResume()");
    // update the view

    view.get().onDataUpdated(state);
  }

  @Override
  public void onBackPressed() {
    // Log.e(TAG, "onBackPressed()");

  }

  @Override
  public void onPause() {
    // Log.e(TAG, "onPause()");
  }

  @Override
  public void onDestroy() {
    // Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onClearPressed() {
    // Log.e(TAG, "onClearPressed()");
    model.onRestartScreen();
    ClicksToCounterState savedState = new ClicksToCounterState();
    savedState.clearClicks=true;
    mediator.setClicksPreviousScreenState(savedState);
    state.clicks= model.getStoredData();
    view.get().onDataUpdated(state);
  }

  private void passStateToPreviousScreen(ClicksToCounterState state) {
    mediator.setClicksPreviousScreenState(state);
  }

  private CounterToClicksState getStateFromPreviousScreen() {
    return mediator.getClicksPreviousScreenState();
  }

  @Override
  public void injectView(WeakReference<ClicksContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(ClicksContract.Model model) {
    this.model = model;
  }

}
