package uk.smarc.android.pumpkin.drawable;

import android.graphics.Point;


/**
 * Represents a Pumpkin object in pumpkin-land. All calls are synchronous.
 */
class Pumpkin {

  /**
   * Position of the centre of the object, in world coordinates.
   */
  private PointF mCentrePosition;

  /**
   * Size of the object, in world coordinates.
   */
  private float mWidth;
  private float mHeight;

  /**
   * The area of a canvas which would be drawn by this shape (i.e. screen
   * coordinates).
   */
  private RectF mDrawBounds;

  /**
   * Sprite sheet containing the sprites
   */
  private Bitmap mSpriteSheet;

  /**
   * Size of each sprite on the sheet. They're assumed all to be the same.
   */
  private float mSpriteWidth;
  private float mSpriteHeight;

  /**
   * List of the sprites this pumpkin may show.
   */
  private Sprite[] mSprites;

  /**
   * Animation list - contains a list of sprite indices and durations.
   *
   * e.g. 1, 2, 3, 4
   *  = display sprite 1 for 2ms; then sprite 3 for 4ms; sprite 1 for 2ms...
   */
  private int[] mAnimationList;
  
  /**
   * Current position in the animation list. This should always be even in the
   * current implementation - see mAnimationList.
   */
  private int mAnimationPosition;

  public Pumpkin() {
  }

  /**
   * Call this to notify the Pumpkin that it should move to the next sprite in
   * its animation list. Should be called once the duration of the previous
   * sprite is up.
   * @return the duration of the new sprite.
   * If this returns a positive number, the pumpkin should now be redrawn.
   */
  public int nextSprite() {
    incrementAnimationPosition();
    return mAnimationList[mAnimationPosition+1];
  }

  /**
   * Commands the pumpkin to draw itself to the given canvas.
   */
  public void draw(Canvas c) {
    Sprite current = mSprites[mAnimationList[mAnimationPosition]];
    // Draw the correct sprite from the sheet in the correct place.
    c.drawBitmap(mSpriteSheet, current.mSpriteRect, mDrawBounds, null);
  }

  /**
   * Set the centre position of the pumpkin, and recalculate other things.
   */
  public void setPosition(Point p) {
    mCentrePosition = new PointF((float)p.x, (float)p.y);
    float w2 = mWidth / 2.0f;
    float h2 = mHeight / 2.0f;
    mDrawBounds = new RectF((float)p.x - w2, (float)p.y - h2, (float)p.x + w2, (float)p.y + h2);
  }

  public void setPosition(PointF p) {
    mCentrePosition = new PointF(p.x, p.y);
    float w2 = mWidth / 2.0f;
    float h2 = mHeight / 2.0f;
    mDrawBounds = new RectF(p.x - w2, p.y - h2, p.x + w2, p.y + h2);
  }

  private void incrementAnimationPosition() {
    mAnimationPosition += 2;
    if (mAnimationList.length >= mAnimationPosition) {
      mAnimationPosition = 0;
    }
  }

  /**
   * Represents a frame in the animation.
   */
  private class Sprite {
    /**
     * Location of the sprite in the sprite sheet.
     */
    public RectF mSpriteRect;
    /**
     * Frame ID.
     */
    public int mId;

    public Sprite(RectF sheetLocation, int id) {
      mSpriteRect = new RectF(sheetLocation);
      mId = id; 
    }
  }
    

}
