package uk.smarc.android.pumpkin.drawable;


public class PumpkinView extends SurfaceView 
  implements SurfaceHolder.Callback/*2*/ {

  private DrawHandler mRenderer;
  private HandlerThread mRendererThread;
  // LoadHandler

  public PumpkinView() {
    mRendererThread = new HandlerThread("Renderer");
    mRenderer = new DrawHandler(mRendererThread.getLooper());
    mRendererThread.start();

    SurfaceHolder holder = getHolder();
    holder.addCallback(this);
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    mRenderer.obtainMessage(DrawHandler.MsgType.SURFACE_CHANGED, width, height, new Integer(format)).sendToTarget();
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    mRenderer.obtainMessage(SURFACE_CREATED, holder).sendToTarget();
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    mRenderer.obtainMessage(SURFACE_DESTROYED).sendToTarget();
  }


  public class DrawHandler extends Handler {

    public enum MsgType {
      // Enum constants
      DRAW,
      SURFACE_CHANGED,
      SURFACE_CREATED,
      SURFACE_DESTROYED,
    }

    private MsgType[] TYPES = MsgTypes.values();

    public DrawHandler(Looper l) {
      super(l);
    }

    public void onMessageReceived(Message msg) {
      switch (TYPES[msg.what]) {
        case DRAW:

        default:
      }
    }

    public void draw() {



      sendEmptyMessage(Draw);
    }

  }

}
