package com.sasiloxr.ghosthand.mouseoverhandler;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.sasiloxr.ghosthand.GhostHandMod;
import com.sasiloxr.ghosthand.utils.Utils;
import com.sun.java.swing.plaf.motif.MotifBorders;
import com.sun.java.swing.plaf.motif.MotifInternalFrameTitlePane;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MouseOverHandler {
    static Minecraft mc = Minecraft.getMinecraft();
    private static Entity pointedEntity;

    public MouseOverHandler(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void handler(MouseEvent event){
       if (event.buttonstate && event.button >= 0 && nullCheck() && GhostHandMod.enabled){
           call();
       }
    }

    private static void call(){
        getEntity();
        System.out.println(mc.thePlayer.getDisplayNameString());
        if (mc.objectMouseOver.entityHit != null){
            System.out.println(mc.objectMouseOver.entityHit.getDisplayName().getUnformattedText());
        }
    }


    private static void getEntity(){
        float partialTicks = 1.0F;
        Entity entity = mc.getRenderViewEntity();
        if (entity == null || mc.theWorld == null){
            return;
        }

        mc.mcProfiler.startSection("pick");
        mc.pointedEntity = null;
        double reach = mc.playerController.getBlockReachDistance();
        mc.objectMouseOver = entity.rayTrace(reach,partialTicks);
        double realReach = reach;
        Vec3 positionEyes = entity.getPositionEyes(partialTicks);
        boolean flag = false;
        int i = 3;

        if (mc.playerController.extendedReach()){
            reach = 6.0D;
            realReach = 6.0D;
        }else{
           if (reach > 3.0D){
               flag = true;
           }
        }

        if (mc.objectMouseOver != null){
            realReach = mc.objectMouseOver.hitVec.distanceTo(positionEyes);
        }

        Vec3 lookVec = entity.getLook(partialTicks);
        Vec3 reachPosition = positionEyes.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach , lookVec.zCoord * reach);

        pointedEntity = null;
        Vec3 vec3 = null;
        float f = 1.0F;


        List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
        {
            public boolean apply(Entity p_apply_1_)
            {
                return p_apply_1_.canBeCollidedWith();
            }
        }));
//        list = Lists.<Entity>newArrayList();
        Iterator<Entity> iterator = list.iterator();
        while (iterator.hasNext()){
            Entity entity1 = iterator.next();
            if (Utils.isTeamMate(entity1)){
                iterator.remove();
            }
        }
        double realreach1 = realReach;
        for (Entity entity1 : list) {
            float CollisionBorderSize = entity1.getCollisionBorderSize();
            AxisAlignedBB axisAlignedBB = entity1.getEntityBoundingBox().expand(CollisionBorderSize, CollisionBorderSize, CollisionBorderSize);
            MovingObjectPosition movingobjectposition = axisAlignedBB.calculateIntercept(positionEyes, reachPosition);

            if (axisAlignedBB.isVecInside(positionEyes)) {
                if (realreach1 >= 0.0D) {
                    pointedEntity = entity1;
                    vec3 = movingobjectposition == null ? positionEyes : movingobjectposition.hitVec;
                    realreach1 = 0.0D;
                }
            } else if (movingobjectposition != null) {
                double realreach2 = positionEyes.distanceTo(movingobjectposition.hitVec);
                if (realreach2 < realreach1 || realreach1 == 0.0D) {
                    if (entity1 == entity.ridingEntity && !entity.canRiderInteract()) {
                        if (realreach1 == 0.0D) {
                            pointedEntity = entity1;
                            vec3 = movingobjectposition.hitVec;
                        }

                    } else {
                        pointedEntity = entity1;
                        vec3 = movingobjectposition.hitVec;
                        realreach1 = realreach2;
                    }
                }

            }

        }
        if (pointedEntity != null && flag && positionEyes.distanceTo(vec3) > 3.0D){
            pointedEntity = null;
            mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec3, (EnumFacing) null, new BlockPos(vec3));
        }

        if (pointedEntity != null && (realreach1 < realReach || mc.objectMouseOver == null)){
            mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec3);
            if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame){
                mc.pointedEntity = pointedEntity;
            }
        }

        mc.mcProfiler.endSection();
    }


    public static boolean nullCheck() {
        return mc.thePlayer != null && mc.theWorld != null;
    }

}
