import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

class MyStar
{
    public Color color;
    public Vector2 position;
    public Vector2 velocity;
    public float xOffset;
    public float yOffset;

    public MyStar(Vector2 position, Vector2 velocity, float opacity)
    {
        this.position = new Vector2();
        this.velocity = new Vector2();

        this.position.x = position.x;
        this.position.y = position.y;
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;

        this.color = new Color(1, 1, 1, opacity);
    }
}

public class StarField
{
    private final List<MyStar> stars;
    private final Vector2 vect2;
    private final Sprite starSpriteA;
    private final Sprite starSpriteB;
    private final Rectangle area; // rectangle area that the star field will be plotted in
    private final StarType starType;
    enum StarType
    {
        BIG,
        SMALL
    }

    public StarField(Assets assets, int starCount, Rectangle area, StarType starType)
    {
        this.area = area;
        starSpriteA = assets.spritesAtlas.createSprite("Star1");
        starSpriteB = assets.spritesAtlas.createSprite("WhitePixel");
        this.starType = starType;

        stars = new ArrayList();
        vect2 = new Vector2();

        for (int i = 0; i < starCount; i++)
        {
            vect2.x = MathUtils.random(area.getX(), area.getWidth());
            vect2.y = MathUtils.random(area.getY(), area.getHeight());

            int type = MathUtils.random(1, 3);

            if (type == 1)      // fastest and brightest
            {
                stars.add(new MyStar(vect2, new Vector2(0, 1.2f), 0.65f));
            }
            else if (type == 2) // standard speed, second brightest
            {
                stars.add(new MyStar(vect2, new Vector2(0, 0.77f), 0.40f));
            }
            else                // slow speed, third brightest
            {
                stars.add(new MyStar(vect2, new Vector2(0, 0.44f), 0.18f));
            }
        }
    }

    public void Update(float xOffset, float yOffset, float velocityMultiplier)
    {
        for (MyStar star : stars)
        {
            // star.position.y += (star.velocity.y * Gdx.graphics.getDeltaTime());
            star.xOffset = xOffset;
            star.yOffset = yOffset;
            star.position.y += (star.velocity.y * velocityMultiplier);

            if (star.position.y > area.getHeight())
            {
                star.position.x = MathUtils.random(area.getX(), area.getWidth());
                star.position.y = area.getY();
            }
        }
    }

    public void Draw(SpriteBatch spriteBatch)
    {
        for (MyStar star : stars)
        {
            if (starType == StarType.BIG)
            {
                starSpriteA.setColor(star.color.r, star.color.g, star.color.b, star.color.a);
                starSpriteA.setPosition(star.position.x + star.xOffset, star.position.y + star.yOffset);
                starSpriteA.draw(spriteBatch);
            }
            else
            {
                starSpriteB.setColor(star.color.r, star.color.g, star.color.b, star.color.a);
                starSpriteB.setPosition(star.position.x + star.xOffset, star.position.y + star.yOffset);
                starSpriteB.draw(spriteBatch);
            }
        }
    }
}
