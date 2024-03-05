import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JPanel implements ActionListener
{
    private List<Box> boxes; // List to store boxes
    private Timer timer;
    private int fallSpeed = 2; // Speed of box falling
    private int floorY; // Y-coordinate of the floor
    private int lastCreatedBoxIndex = -1; // Index of the last created box

    public Main()
    {
        boxes = new ArrayList<>();
        timer = new Timer(10, this);
        timer.start();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createBox(e.getX());
            }
        });

        this.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                handleKeyPress(e);
            }
        });
        this.setFocusable(true);
    }

    private void handleKeyPress(KeyEvent e)
    {

        if (lastCreatedBoxIndex != -1)
        {
            int keyCode = e.getKeyCode();
            Box lastBox = boxes.get(lastCreatedBoxIndex);

            if (keyCode == KeyEvent.VK_LEFT)
            {
                lastBox.moveLeft(boxes);
            }
            else if (keyCode == KeyEvent.VK_RIGHT)
            {
                lastBox.moveRight(getWidth(), boxes);
            }
            repaint();
        }

    }

    private void createBox(int x)
    {
        Box newBox = new Box(x, 0, 50, 50);
        boxes.add(newBox);
        lastCreatedBoxIndex = boxes.size() - 1; // Update the index of the last created box
        repaint();
    }

    public void actionPerformed(ActionEvent e)
    {
        for (int i = 0; i < boxes.size(); i++)
        {
            Box box = boxes.get(i);

            if (box.getY() + box.getHeight() < floorY && !isTouchingOtherBoxes(i))
            {
                box.setY(box.getY() + fallSpeed);

            }
        }
        repaint();
    }

    private boolean isTouchingOtherBoxes(int currentIndex)
    {
        Box currentBox = boxes.get(currentIndex);
        for (int i = 0; i < boxes.size(); i++)
        {
            if (i != currentIndex)
            {
                Box otherBox = boxes.get(i);
                if (currentBox.isTouching(otherBox))
                {
                    return true;
                }
            }
        }
        return false;
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Draw the floor
        g.setColor(Color.GRAY);
        g.fillRect(0, floorY, getWidth(), getHeight() - floorY);

        // Draw the falling boxes
        g.setColor(Color.BLUE);
        for (Box box : boxes)
        {
            g.fillRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Box Drop Game");
        Main game = new Main();
        game.floorY = 600; // Set the y-coordinate of the floor

        frame.add(game);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}