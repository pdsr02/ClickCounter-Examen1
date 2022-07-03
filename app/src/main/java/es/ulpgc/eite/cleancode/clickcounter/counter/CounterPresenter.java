package es.ulpgc.eite.cleancode.clickcounter.counter;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.clickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.clickcounter.app.ClicksToCounterState;
import es.ulpgc.eite.cleancode.clickcounter.app.CounterToClicksState;

public class CounterPresenter implements CounterContract.Presenter {

  public static String TAG = CounterPresenter.class.getSimpleName();

  private WeakReference<CounterContract.View> view;
  private CounterState state;
  private CounterContract.Model model;

  private AppMediator mediator;

  public CounterPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getCounterState();
  }


  @Override
  public void onStart() {
    //Solo se ejecuta cuando se crea la pantalla por primera vez
    Log.e(TAG, "onStart()");

    // initialize the state if is necessary
    if (state == null) {
      state = new CounterState();
    }
    //No hay nada en el modelo no hace falta
    //state.counter = model.getStoredData();
  }

  @Override
  public void onRestart() {
    //Se ejecuta cada que se gira la pantalla
    Log.e(TAG, "onRestart()");

    //Rara vez hay que recuperar los datos del modelo
    //para inicializar el estado porque este ha podido cambiar
   //state.counter = model.getStoredData();
  }

  @Override
  public void onResume() {
    /*Se ejecuta siempre en los siguientes casos:
    1) Despues del metodo onStart() al iniciar la pantalla por primera vez
    2) Despues del metodo onRestart() al girar la pantalla
    3) Justo despues de finalizar la pantalla que estaba antes
    */
    Log.e(TAG, "onResume()");

    // use passed state if is necessary
    ClicksToCounterState savedState = getStateFromNextScreen();
    if (savedState != null) {
      if(savedState.clearClicks==true) {
        model.restoredClicks(true);
      }
    }

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
  public void onClicksPressed() {
    // Log.e(TAG, "onClicksPressed()");
    CounterToClicksState savedState = new CounterToClicksState();
    savedState.clicks=model.getNumClicks();
    passStateToNextScreen(savedState);
    view.get().navigateToNextScreen();
  }

  @Override
  public void onResetPressed() {
    // Log.e(TAG, "onResetPressed()");
    model.restartData();
    state.counter = model.getStoredData();
    view.get().onDataUpdated(state);
  }

  @Override
  public void onIncrementPressed() {
    // Log.e(TAG, "onIncrementPressed()");
    model.incrementCounter();
    state.counter = model.getStoredData();
    view.get().onDataUpdated(state);
  }

  private void passStateToNextScreen(CounterToClicksState state) {
    mediator.setCounterNextScreenState(state);
  }

  private ClicksToCounterState getStateFromNextScreen() {
    return mediator.getCounterNextScreenState();
  }

  @Override
  public void injectView(WeakReference<CounterContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CounterContract.Model model) {
    this.model = model;
  }

}
