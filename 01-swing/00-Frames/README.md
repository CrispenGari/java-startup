### Frame

A JFrame act like a main window where components are being added when creating a Graphic User Interface(GUI).

I this readme we are going to look at how to create a simple window wih code using Java Swing. So basically in this section we are going to
look at the `JFrame`.

### Creating a simple window
To create a simple window we are going to create it as follows.
```java
package com.company;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame(); // this creates a frame
        frame.setVisible(true); // this makes a frame visible
    }
}
```

### Setting dimensions of a window
To set the dimensions of the window we do it as follows:
```java
frame.setSize(new Dimension(200, 200)); // alternatively frame.setSize(200, 200)
```
### Disable resizable
To disable the window from being resized we do it as follows:
```java
frame.setResizable(false);
```
### Change the Icon Image

To change the icon image of the frame we do it as follows:
```java
ImageIcon image = new ImageIcon("main.svg");
frame.setIconImage(image.getImage());
```
### Setting the title
To set the title of the frame or window we call the `setTitle` method on the frame as follows:
```java
frame.setTitle("My Frame");
```
### Changing the background Color of the window
To change the background color of the window or frame we do it as follows:

```java
frame.getContentPane().setBackground(new Color(0, 0, 0)); // new Color(0x000000) or Color.black
```

### Default operation on the Close Button
By default when you click the `exit` button on the frame it does nothing because the operation that is set to it
is called  `JFrame.HIDE_ON_CLOSE` but we want to close the program then we click the exit button therefore we are going to
change the defaultCloseOperation to `JFrame.EXIT_ON_CLOSE` as follows:

```java
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
```

So far our `Main.java` file is being populated before adding any components to this window. It has the following code in it as for now:

```java
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame(); // this creates a frame
        frame.setSize(new Dimension(200, 200)); // alternatively frame.setSize(200, 200)
        frame.setResizable(false);
        // Create an icon
        ImageIcon image = new ImageIcon("info.png");
        frame.setIconImage(image.getImage());
        frame.setTitle("My Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0, 0, 0)); // new Color(0x000000)

        frame.setVisible(true); // this makes a frame visible
    }
}
```
We can refactor the code by creating a new external class in the package called frames, in that package we will
create a new java class called MainFrame which will inherit from the `JFrame` class as follows:

```java
package com.company.frames;

import javax.swing.*;
import java.awt.*;

public class MainFrame  extends JFrame {
    public MainFrame(){
        this.setSize(new Dimension(200, 200)); // alternatively frame.setSize(200, 200)
        this.setResizable(false);
        // Create an icon
        ImageIcon image = new ImageIcon("info.png");
        this.setIconImage(image.getImage());
        this.setTitle("My Frame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0, 0, 0)); // new Color(0x000000)

        this.setVisible(true); // this makes a frame visible
    }
}
```
In the `Main.java` we are going to have the following code in it:

```java
package com.company;
import com.company.frames.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
    }
}
```
I the next section we are going to look at `JLabels`

### Ref
1. [javapoint](https://www.javatpoint.com/java-jframe)