import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class Box
{
    private int x;
    private int y;
    private int width;
    private int height;

    public Box(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void moveLeft(List<Box> boxes)
    {
        int targetX = x - 10; // Calculate the target X position after the move
        if (targetX < 0)
        {
            targetX = 0;  // Ensure the box stays within the left boundary
        }

        if (!isCollidingWithOtherBoxes(targetX, y, boxes))
        {
            x = targetX;  // Move the box if not colliding
        }
    }

    public void moveRight(int panelWidth, List<Box> boxes)
    {
        int targetX = x + 10; // Calculate the target X position after the move
        int maxX = panelWidth - width; // Maximum allowed X position

        if (targetX > maxX)
        {
            targetX = maxX;  // Ensure the box stays within the right boundary
        }

        if (!isCollidingWithOtherBoxes(targetX, y, boxes))
        {
            x = targetX;  // Move the box if not colliding
        }
    }

    private boolean isCollidingWithOtherBoxes(int targetX, int targetY, List<Box> boxes)
    {
        for (Box otherBox : boxes)
        {
            if (otherBox != this)
            {
                if (targetX < otherBox.x + otherBox.width &&
                        targetX + width > otherBox.x &&
                        targetY < otherBox.y + otherBox.height &&
                        targetY + height > otherBox.y)
                {
                    return true;  // Collision detected
                }
            }
        }
        return false;  // No collision detected
    }

    public boolean isTouching(Box otherBox)
    {
        return this.x < otherBox.x + otherBox.width &&
                this.x + this.width > otherBox.x &&
                this.y < otherBox.y + otherBox.height &&
                this.y + this.height > otherBox.y;
    }
}
