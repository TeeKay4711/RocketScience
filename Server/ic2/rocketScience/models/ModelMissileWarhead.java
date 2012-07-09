// Date: 10/11/2011 10:47:08 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX





package ic2.rocketScience.models;

import net.minecraft.src.*;

public class ModelMissileWarhead extends ModelBase
{
    public ModelMissileWarhead()
    {
        New_Shape5 = new ModelRenderer(this, 18, 21);
        New_Shape5.addBox(-4F, 0F, -4F, 4, 1, 4);
        New_Shape5.setRotationPoint(2F, 23F, 2F);
        New_Shape5.rotateAngleX = 0F;
        New_Shape5.rotateAngleY = 0F;
        New_Shape5.rotateAngleZ = 0F;
        New_Shape5.mirror = false;
        New_Shape4 = new ModelRenderer(this, 0, 21);
        New_Shape4.addBox(0F, 0F, 0F, 6, 2, 6);
        New_Shape4.setRotationPoint(-3F, 21F, -3F);
        New_Shape4.rotateAngleX = 0F;
        New_Shape4.rotateAngleY = 0F;
        New_Shape4.rotateAngleZ = 0F;
        New_Shape4.mirror = false;
        New_Shape1 = new ModelRenderer(this, 0, 0);
        New_Shape1.addBox(0F, 0F, 0F, 7, 13, 7);
        New_Shape1.setRotationPoint(-3.5F, 8F, -3.5F);
        New_Shape1.rotateAngleX = 0F;
        New_Shape1.rotateAngleY = 0F;
        New_Shape1.rotateAngleZ = 0F;
        New_Shape1.mirror = false;
        New_Shape23 = new ModelRenderer(this, 0, 20);
        New_Shape23.addBox(0F, 0F, 0F, 1, 6, 1);
        New_Shape23.setRotationPoint(-0.5F, 8F, -4.5F);
        New_Shape23.rotateAngleX = 0F;
        New_Shape23.rotateAngleY = 0F;
        New_Shape23.rotateAngleZ = 0F;
        New_Shape23.mirror = false;
        New_Shape21 = new ModelRenderer(this, 0, 20);
        New_Shape21.addBox(0F, 0F, 0F, 1, 6, 1);
        New_Shape21.setRotationPoint(3.5F, 8F, -0.5F);
        New_Shape21.rotateAngleX = 0F;
        New_Shape21.rotateAngleY = 0F;
        New_Shape21.rotateAngleZ = 0F;
        New_Shape21.mirror = false;
        New_Shape2 = new ModelRenderer(this, 0, 20);
        New_Shape2.addBox(0F, 0F, 0F, 1, 6, 1);
        New_Shape2.setRotationPoint(-4.5F, 8F, -0.5F);
        New_Shape2.rotateAngleX = 0F;
        New_Shape2.rotateAngleY = 0F;
        New_Shape2.rotateAngleZ = 0F;
        New_Shape2.mirror = false;
        New_Shape22 = new ModelRenderer(this, 0, 20);
        New_Shape22.addBox(0F, 0F, 0F, 1, 6, 1);
        New_Shape22.setRotationPoint(-0.5F, 8F, 3.5F);
        New_Shape22.rotateAngleX = 0F;
        New_Shape22.rotateAngleY = 0F;
        New_Shape22.rotateAngleZ = 0F;
        New_Shape22.mirror = false;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        New_Shape5.render(f5);
        New_Shape4.render(f5);
        New_Shape1.render(f5);
        New_Shape23.render(f5);
        New_Shape21.render(f5);
        New_Shape2.render(f5);
        New_Shape22.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }

    //fields
    ModelRenderer New_Shape5;
    ModelRenderer New_Shape4;
    ModelRenderer New_Shape1;
    ModelRenderer New_Shape23;
    ModelRenderer New_Shape21;
    ModelRenderer New_Shape2;
    ModelRenderer New_Shape22;
}