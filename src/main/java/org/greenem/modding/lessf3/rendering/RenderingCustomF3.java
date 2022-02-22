package org.greenem.modding.lessf3.rendering;

import org.greenem.general.Patterns;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class RenderingCustomF3 {
    public static void changeF3Text(RenderGameOverlayEvent.Text e) {
        ArrayList<String> list1 = e.getLeft();
        ArrayList<String> list2 = e.getRight();
        String[] modified = new String[list1.size()];
        Matcher matcher;

        // LEFT SIDE
        for (String s : list1) {
            //System.out.println(s);
            // Start checking everything
            matcher = Patterns.fps2.matcher(s);
            if (matcher.find()) {
                modified[0] = matcher.group();
            }
            else {
                matcher = Patterns.fps1.matcher(s);
                if (matcher.find()) {
                    modified[0] = matcher.group();
                }
            }
            if (s.startsWith("Block: ")) {
                modified[1] = s;
            }
            if (s.startsWith("Chunk: ")) {
                modified[2] = s;
            }
            if (s.startsWith("Facing: ")) {
                modified[3] = s;
            }
            matcher = Patterns.entitiesPart.matcher(s);
            if (matcher.find()) {
                modified[4] = matcher.group();
            }
            if (s.startsWith("SH S: ")) {
                modified[5] = s;
            }
            if (s.startsWith("Biome: ")) {
                modified[6] = s;
            }
        }

        list1.clear();
        for (int i = 0; i < modified.length; i++) {
            if(modified[i]!=null) {
                list1.add(modified[i]);
            }
        }

        // RIGHT SIDE
        modified = new String[list2.size()];
        for (String s : list2) {
            //System.out.println(s);
            if (s.contains("Targeted Block: ")) {
                modified[0] = s.substring(2);
            }
            if (s.startsWith("minecraft:")) {
                modified[1] = s;
                break;
            }
        }

        list2.clear();
        for (int i = 0; i < modified.length; i++) {
            if(modified[i]!=null) {
                list2.add(modified[i]);
            }
        }
    }
}
