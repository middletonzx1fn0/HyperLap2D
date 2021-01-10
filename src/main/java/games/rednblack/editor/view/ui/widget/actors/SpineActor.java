package games.rednblack.editor.view.ui.widget.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.BoneData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.Attachment;
import com.esotericsoftware.spine.attachments.MeshAttachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import games.rednblack.editor.renderer.resources.IResourceRetriever;

public class SpineActor extends Actor {

    private String animationName;
    public SkeletonData skeletonData;
    private SkeletonRenderer renderer;
    private Skeleton skeleton;
    private AnimationState state;
    private IResourceRetriever irr;
    private SkeletonJson skeletonJson;
    private float minX = 0;
    private float minY = 0;
    private FloatArray temp;

    public SpineActor(String animationName, IResourceRetriever irr) {
        temp = new FloatArray();
        this.irr = irr;
        this.renderer = new SkeletonRenderer();
        this.animationName = animationName;
        initSkeletonData();
        initSpine();
    }

    private void computeBoundBox() {
        skeleton.updateWorldTransform();

        Array<Slot> drawOrder = skeleton.getDrawOrder();
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;
        float maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (int i = 0, n = drawOrder.size; i < n; i++) {
            Slot slot = drawOrder.get(i);
            if (!slot.getBone().isActive()) continue;
            int verticesLength = 0;
            float[] vertices = null;
            Attachment attachment = slot.getAttachment();
            if (attachment instanceof RegionAttachment) {
                verticesLength = 8;
                vertices = temp.setSize(8);
                ((RegionAttachment)attachment).computeWorldVertices(slot.getBone(), vertices, 0, 2);
            } else if (attachment instanceof MeshAttachment) {
                MeshAttachment mesh = (MeshAttachment)attachment;
                verticesLength = mesh.getWorldVerticesLength();
                vertices = temp.setSize(verticesLength);
                mesh.computeWorldVertices(slot, 0, verticesLength, vertices, 0, 2);
            }
            if (vertices != null) {
                for (int ii = 0; ii < verticesLength; ii += 2) {
                    float x = vertices[ii], y = vertices[ii + 1];
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }
        setWidth(maxX - minX);
        setHeight(maxY - minY);
    }

    private void initSkeletonData() {
        skeletonJson = new SkeletonJson(irr.getSkeletonAtlas(animationName));
        skeletonData = skeletonJson.readSkeletonData((irr.getSkeletonJSON(animationName)));
    }

    private void initSpine() {
        BoneData root = skeletonData.getBones().get(0);
        root.setScale(getScaleX(), getScaleY());
        skeleton = new Skeleton(skeletonData);
        AnimationStateData stateData = new AnimationStateData(skeletonData);
        state = new AnimationState(stateData);
        computeBoundBox();
        setAnimation(skeletonData.getAnimations().get(0).getName());
    }

    public Array<Animation> getAnimations() {
        return skeletonData.getAnimations();
    }

    public void setAnimation(String animName) {
        state.setAnimation(0, animName, true);
    }

    public AnimationState getState() {
        return state;
    }

    @Override
    public void setScale(float scale) {
        super.setScale(scale);
        initSpine();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = skeleton.getColor();

        float oldAlpha = color.a;
        skeleton.getColor().a *= parentAlpha;
        renderer.draw((PolygonSpriteBatch)batch, skeleton);
        color.a = oldAlpha;

        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        skeleton.updateWorldTransform(); //
        state.update(delta);
        state.apply(skeleton);
        skeleton.setPosition(getX() - minX, getY() - minY);
        super.act(delta);
    }
}
