/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javafx.scene.canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.RectBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.scene.DirtyBits;
import com.sun.javafx.sg.prism.GrowableDataBuffer;
import com.sun.javafx.sg.prism.NGCanvas;
import com.sun.javafx.sg.prism.NGNode;

/**
 * {@code Canvas} is an image that can be drawn on using a set of graphics
 * commands provided by a {@code GraphicsContext}.
 *
 * <p>
 * A {@code Canvas} node is constructed with a width and height that specifies the size
 * of the image into which the canvas drawing commands are rendered. All drawing
 * operations are clipped to the bounds of that image.
 *
 * <p>Example:</p>
 *
 * <p>
 * <pre>
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;

Group root = new Group();
Scene s = new Scene(root, 300, 300, Color.BLACK);

final Canvas canvas = new Canvas(250,250);
GraphicsContext gc = canvas.getGraphicsContext2D();

gc.setFill(Color.BLUE);
gc.fillRect(75,75,100,100);

root.getChildren().add(canvas);
 * </pre>
 * </p>
 *
 * @since JavaFX 2.2
 */
public class Canvas extends Node {
    static final int DEFAULT_VAL_BUF_SIZE = 1024;
    static final int DEFAULT_OBJ_BUF_SIZE = 32;
    private static final int SIZE_HISTORY = 5;

    private GrowableDataBuffer current;
    private boolean rendererBehind;
    private int recentvalsizes[];
    private int recentobjsizes[];
    private int lastsizeindex;

    private GraphicsContext theContext;

    /**
     * Creates an empty instance of Canvas.
     */
    public Canvas() {
        this(0, 0);
    }

    /**
     * Creates a new instance of Canvas with the given size.
     *
     * @param width width of the canvas
     * @param height height of the canvas
     */
    public Canvas(double width, double height) {
        this.recentvalsizes = new int[SIZE_HISTORY];
        this.recentobjsizes = new int[SIZE_HISTORY];
        setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        setWidth(width);
        setHeight(height);
    }

    private static int max(int sizes[], int defsize) {
        for (int s : sizes) {
            if (defsize < s) defsize = s;
        }
        return defsize;
    }

    GrowableDataBuffer getBuffer() {
        impl_markDirty(DirtyBits.NODE_CONTENTS);
        impl_markDirty(DirtyBits.NODE_FORCE_SYNC);
        if (current == null) {
            int vsize = max(recentvalsizes, DEFAULT_VAL_BUF_SIZE);
            int osize = max(recentobjsizes, DEFAULT_OBJ_BUF_SIZE);
            current = GrowableDataBuffer.getBuffer(vsize, osize);
            theContext.updateDimensions();
        }
        return current;
    }

    boolean isRendererFallingBehind() {
        return rendererBehind;
    }

    /**
     * returns the {@code GraphicsContext} associated with this {@code Canvas}.
     */
    public GraphicsContext getGraphicsContext2D() {
        if (theContext == null) {
            theContext = new GraphicsContext(this);
        }
        return theContext;
    }

    /**
     * Defines the width of the canvas.
     *
     * @profile common
     * @defaultvalue 0.0
     */
    private DoubleProperty width;

    public final void setWidth(double value) {
        widthProperty().set(value);
    }

    public final double getWidth() {
        return width == null ? 0.0 : width.get();
    }

    public final DoubleProperty widthProperty() {
        if (width == null) {
            width = new DoublePropertyBase() {

                @Override
                public void invalidated() {
                    impl_markDirty(DirtyBits.NODE_GEOMETRY);
                    impl_geomChanged();
                    if (theContext != null) {
                        theContext.updateDimensions();
                    }
                }

                @Override
                public Object getBean() {
                    return Canvas.this;
                }

                @Override
                public String getName() {
                    return "width";
                }
            };
        }
        return width;
    }

    /**
     * Defines the height of the canvas.
     *
     * @profile common
     * @defaultvalue 0.0
     */
    private DoubleProperty height;

    public final void setHeight(double value) {
        heightProperty().set(value);
    }

    public final double getHeight() {
        return height == null ? 0.0 : height.get();
    }

    public final DoubleProperty heightProperty() {
        if (height == null) {
            height = new DoublePropertyBase() {

                @Override
                public void invalidated() {
                    impl_markDirty(DirtyBits.NODE_GEOMETRY);
                    impl_geomChanged();
                    if (theContext != null) {
                        theContext.updateDimensions();
                    }
                }

                @Override
                public Object getBean() {
                    return Canvas.this;
                }

                @Override
                public String getName() {
                    return "height";
                }
            };
        }
        return height;
    }

    /**
     * @treatAsPrivate implementation detail
     * @deprecated This is an internal API that is not intended for use and will be removed in the next version
     */
    @Deprecated
    @Override protected NGNode impl_createPeer() {
        return new NGCanvas();
    }

    /**
     * @treatAsPrivate implementation detail
     * @deprecated This is an internal API that is not intended for use and will be removed in the next version
     */
    @Deprecated
    @Override
    public void impl_updatePeer() {
        super.impl_updatePeer();
        if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
            NGCanvas peer = impl_getPeer();
            peer.updateBounds((float)getWidth(),
                              (float)getHeight());
        }
        if (impl_isDirty(DirtyBits.NODE_CONTENTS)) {
            NGCanvas peer = impl_getPeer();
            if (current != null && !current.isEmpty()) {
                if (--lastsizeindex < 0) {
                    lastsizeindex = SIZE_HISTORY - 1;
                }
                recentvalsizes[lastsizeindex] = current.writeValuePosition();
                recentobjsizes[lastsizeindex] = current.writeObjectPosition();
                rendererBehind = peer.updateRendering(current);
                current = null;
            }
        }
    }

    /**
     * @treatAsPrivate implementation detail
     * @deprecated This is an internal API that is not intended for use and will be removed in the next version
     */
    @Deprecated
    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        double w = getWidth();
        double h = getHeight();
        return (w > 0 && h > 0 &&
                localX >= 0 && localY >= 0 &&
                localX <  w && localY <  h);
    }

    /**
     * @treatAsPrivate implementation detail
     * @deprecated This is an internal API that is not intended for use and will be removed in the next version
     */
    @Deprecated
    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        bounds = new RectBounds(0f, 0f, (float) getWidth(), (float) getHeight());
        bounds = tx.transform(bounds, bounds);
        return bounds;
    }

    /**
     * @treatAsPrivate implementation detail
     * @deprecated This is an internal API that is not intended for use and will be removed in the next version
     */
    @Deprecated
    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg,
                                     MXNodeAlgorithmContext ctx) {
        return alg.processLeafNode(this, ctx);
    }
}
