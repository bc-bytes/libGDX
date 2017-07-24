import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/** 
* MyStar class represents a single star object in the starfield.
*
* @author bc_bytes
*/
class MyStar
{
    public Color color;
    public Vector2 position;
    public Vector2 velocity;
    public float xOffset;
    public float yOffset;

    /** 
    * MyStar class constructor.
    * 
    * @param position Vector2 containing the starting position of the star object.
    * @param velocity Vector2 containing the horizontal and vertical velocity of the star object.
    * @param opacity  Opacity of the star object.
    */
    public MyStar(Vector2 position, Vector2 velocity, float opacity)
    {
        this.position = new Vector2(position.x, position.y);
        this.velocity = new Vector2(velocity.x, velocity.y);
        this.color = new Color(1, 1, 1, opacity);
    }
}

/** 
* StarField class processes and draws a custom starfield containing scrolling stars. Starfield simulates three parallax layers.
* 
* Uses snippets from https://stackoverflow.com/questions/31889513/canvas-rotating-star-field
*
* @author bc_bytes
*/
public class StarField
{
    private final List<MyStar> stars;
    private final Vector2 vect2;
    private final Sprite starSpriteA;
    private final Sprite starSpriteB;
    private final Rectangle area;
    private final StarType starType;
    enum StarType
    {
        BIG,
        SMALL
    }

    /** 
    * StarField class constructor.
    * 
    * @param spriteAtlas A SpriteAtlas object containing the sprites of the star objects.
    * @param starCount   The total number of star objects that the starfield will contain.
    * @param area        A Rectangle object that will restrict where on the screen the starfield will be plotted.
    * @param starType    What type of star will be used in the starfield.
    */
    public StarField(SpritesAtlas spritesAtlas, int starCount, Rectangle area, StarType starType)
    {
        this.area = area;
        starSpriteA = spritesAtlas.createSprite("Star1");
        starSpriteB = spritesAtlas.createSprite("Star2");
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

    /** 
    * Update method updates the positions of all stars in the starfield. Call this every frame in your main update method.
    * Use the offset parameters if the starfield area position changes.
    * 
    * @param xOffset            Offset position for x-axis of the star objects.
    * @param yOffset            Offset position for y-axis of the star objects.
    * @param velocityMultiplier Set this value to change the velocity of all star objects.
    */
    public void Update(float xOffset, float yOffset, float velocityMultiplier)
    {
        for (MyStar star : stars)
        {
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

    /** 
    * Draw method draws all star objects in the starfield to screen. Call this every frame in your main draw method.
    * 
    * @param spriteBatch An initialised SpriteBatch object.
    */
    public void Draw(SpriteBatch spriteBatch)
    {
        for (MyStar star : stars)
        {
            starSpriteA.setColor(star.color.r, star.color.g, star.color.b, star.color.a);
            starSpriteA.setPosition(star.position.x + star.xOffset, star.position.y + star.yOffset);
            
            if (starType == StarType.BIG)
            {                
                starSpriteA.draw(spriteBatch);
            }
            else
            {
                starSpriteB.draw(spriteBatch);
            }
        }
    }
}
