### Labels
The object of JLabel class is a component for placing text in a container.
 It is used to display a single line of read only text.
The text can be changed by an application but a user cannot edit it directly.

In this section we are going to look at how we can create a label and mount it on the frame using java.

> In a `JLabel` component text is rendered on a label based on the icon of the label and can be shifted around.

### Creating a label with Icon and Text
To create a simple label with icon and text we do it as follows:
```java
package com.company;

import javax.swing.*;
public class Main {
    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = Main.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    public static void main(String[] args) {

        JFrame frame = new JFrame("JLabel");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("My label is awesome");
        label.setIcon(createImageIcon("info.jpg", "-"));

        // Adding a label to the frame
        frame.add(label);
        frame.setVisible(true);
    }
}
```
> That's the basics about creating and adding the label to the frame.

### Ref

1. [javatpoint](https://www.javatpoint.com/java-jlabel)