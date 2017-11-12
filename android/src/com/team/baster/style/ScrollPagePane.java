package com.team.baster.style;

/**
 * Created by Smeet on 05.11.2017.
 */

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class ScrollPagePane extends ScrollPane {

    private boolean wasPanDragFling = false;

    private Table content;

    public ScrollPagePane () {
        super(null);
        setup();
    }

    private void setup() {
        content = new Table();
        content.defaults().space(20);
        super.setWidget(content);
    }

    public void addPages (Actor... pages) {
        for (Actor page : pages) {
            content.add(page).expandY().fillY();
        }
    }

    public void addPage (Actor page) {
        content.add(page).expandY().fillY();
    }

    @Override
    public void act (float delta) {
        super.act(delta);
        if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
            wasPanDragFling = false;
            scrollToPage();
        } else {
            if (isPanning() || isDragging() || isFlinging()) {
                wasPanDragFling = true;
            }
        }
    }

    @Override
    public void setWidget (Actor widget) {
        throw new UnsupportedOperationException("Use PagedScrollPane#addPage.");
    }
//
//    @Override
//    public void setWidth (float width) {
//        super.setWidth(width);
//        if (content != null) {
//            for (Cell cell : content.getCells()) {
//                cell.width(width);
//            }
//            content.invalidate();
//        }
//    }
//
    public void setPageSpacing (float pageSpacing) {
        if (content != null) {
            content.defaults().space(pageSpacing);
            for (Cell cell : content.getCells()) {
                cell.space(pageSpacing);
            }
            content.invalidate();
        }
    }

    public int calculateCurrentSection() {

        int section = Math.round( getScrollX() / getWidth()) + 1;
        if ( section > content.getChildren().size ) return content.getChildren().size;
        if ( section < 1 ) return 1;
        return section;
    }

    public int getSectionsCount() {
        return content.getChildren().size;
    }

    private void scrollToPage () {
        final float width = getWidth();
        final float scrollX = getScrollX();
        final float maxX = getMaxX();

        if (scrollX >= maxX || scrollX <= 0) return;

        Array<Actor> pages = content.getChildren();
        float pageX = 0;
        float pageWidth = 0;
        if (pages.size > 0) {
            for (Actor a : pages) {
                pageX = a.getX();
                pageWidth = a.getWidth();
                if (scrollX < (pageX + pageWidth * 0.5)) {
                    break;
                }
            }
            setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2, 0, maxX));
        }
    }

}
