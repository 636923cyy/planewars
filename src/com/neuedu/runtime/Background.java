package com.neuedu.runtime;

        import com.neuedu.base.BaseSprite;
        import com.neuedu.base.Drawable;
        import com.neuedu.base.Moveable;

        import java.awt.*;

public class Background extends BaseSprite implements Drawable, Moveable {

    private Image bgImage;

    public Background() {
    }

    public Background(int x, int y, Image bgImage) {
        super(x, y);
        this.bgImage = bgImage;
    }

    @Override
    public void move() {

    }

    @Override
    public void draw(Graphics g) {

    }
}
