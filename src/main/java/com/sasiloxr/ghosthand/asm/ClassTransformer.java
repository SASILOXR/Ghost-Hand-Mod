package com.sasiloxr.ghosthand.asm;

import jdk.nashorn.internal.runtime.ListAdapter;
import jdk.nashorn.internal.runtime.Source;
import net.minecraft.item.ItemSnow;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer, Opcodes {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        boolean findTeamInvisibleClass = transformedName.equals("net.minecraft.client.renderer.entity.layers.LayerArmorBase");
        boolean findGhostHandClass = transformedName.equals("net.minecraft.client.renderer.EntityRenderer");
        if (findTeamInvisibleClass) {
            return transformClass(basicClass);
        }
        if (findGhostHandClass) {
            System.out.println("find ghost hand class");
            return transformClass2(basicClass);
        }
        return basicClass;
    }

    public byte[] transformClass(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        ClassReader reader = new ClassReader(basicClass);
        reader.accept(classNode, 0);
        for (MethodNode methodNode : classNode.methods) {
            boolean isRenderLayer = methodNode.name.equals(ASMLoadingPlugin.isObf ? "a" : "renderLayer") && methodNode.desc.equals(ASMLoadingPlugin.isObf ? "(Lpr;FFFFFFFI)V" : "(Lnet/minecraft/entity/EntityLivingBase;FFFFFFFI)V");
            if (isRenderLayer) {
                for (AbstractInsnNode insNode : methodNode.instructions.toArray()) {
                    if (isFindNode(insNode)) {
                        AbstractInsnNode previous = insNode.getPrevious();
                        AbstractInsnNode previous1 = previous.getPrevious();
                        AbstractInsnNode previous2 = previous1.getPrevious();

                        LabelNode labelNode = new LabelNode();
                        methodNode.instructions.insertBefore(previous2, labelNode);


                        InsnList list = new InsnList();
                        methodNode.instructions.insertBefore(previous2, new FieldInsnNode(GETSTATIC, "com/sasiloxr/ghosthand/asm/TeamInvisibleHook", "INSTANCE", "Lcom/sasiloxr/ghosthand/asm/TeamInvisibleHook;"));
                        list.add(new FieldInsnNode(GETSTATIC, "com/sasiloxr/ghosthand/asm/TeamInvisibleHook", "INSTANCE", "Lcom/sasiloxr/ghosthand/asm/TeamInvisibleHook;"));
                        list.add(new VarInsnNode(ALOAD, 1));
                        list.add(new MethodInsnNode(INVOKEVIRTUAL, "com/sasiloxr/ghosthand/asm/TeamInvisibleHook", "shouldRenderLayer", ASMLoadingPlugin.isObf ? "(Lpr;)Z" : "(Lnet/minecraft/entity/EntityLivingBase;)Z"));
                        list.add(new JumpInsnNode(IFNE, labelNode));
                        list.add(new InsnNode(RETURN));

                        methodNode.instructions.insertBefore(labelNode, list);


//                        methodNode.instructions.remove(previous2);
//                        methodNode.instructions.remove(previous);
//                        list.add(new InsnNode(ACONST_NULL));
//                        methodNode.instructions.insert(insNode, list);
//                        methodNode.instructions.remove(insNode);
                    }
                }
            }

        }
        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    public byte[] transformClass2(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        ClassReader reader = new ClassReader(basicClass);
        reader.accept(classNode, 0);
        for (MethodNode methodNode : classNode.methods) {
            boolean isGetMouseOver = methodNode.name.equals(ASMLoadingPlugin.isObf ? "a" : "getMouseOver") && methodNode.desc.equals("(F)V");
            if (isGetMouseOver) {
                System.out.println("find mouseover");
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (isFindNode2(insnNode)) {
                        System.out.println("find insnnode");
                        //add some abstractinsnnode
                        AbstractInsnNode next = insnNode.getNext();
                        InsnList list = new InsnList();
                        list.add(new FieldInsnNode(GETSTATIC, "com/sasiloxr/ghosthand/asm/GhostHandHook", "INSTANCE", "Lcom/sasiloxr/ghosthand/asm/GhostHandHook;"));
                        list.add(new VarInsnNode(ALOAD, 14));
                        list.add(new MethodInsnNode(INVOKEVIRTUAL, "com/sasiloxr/ghosthand/asm/GhostHandHook", "handlerList", "(Ljava/util/List;)Ljava/util/List;"));
                        list.add(new VarInsnNode(ASTORE, 14));
                        methodNode.instructions.insert(next, list);
                        System.out.println("success inject");

                    }
                }
            }
        }
        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        // finish all to change
        return classWriter.toByteArray();
    }

    public Boolean isFindNode(AbstractInsnNode insnNode) {
        return insnNode instanceof MethodInsnNode && insnNode.getOpcode() == INVOKEVIRTUAL && ((MethodInsnNode) insnNode).name.equals(ASMLoadingPlugin.isObf ? "a" : "getCurrentArmor") && ((MethodInsnNode) insnNode).owner.equals(ASMLoadingPlugin.isObf ? "bkn" : "net/minecraft/client/renderer/entity/layers/LayerArmorBase") && ((MethodInsnNode) insnNode).desc.equals(ASMLoadingPlugin.isObf ? "(Lpr;I)Lzx;" : "(Lnet/minecraft/entity/EntityLivingBase;I)Lnet/minecraft/item/ItemStack;");

    }

    public Boolean isFindNode2(AbstractInsnNode insnNode) {
        return insnNode instanceof MethodInsnNode && insnNode.getOpcode() == INVOKEVIRTUAL && ((MethodInsnNode) insnNode).name.equals(ASMLoadingPlugin.isObf ? "a" : "getEntitiesInAABBexcluding") && ((MethodInsnNode) insnNode).owner.equals(ASMLoadingPlugin.isObf ? "bdb" : "net/minecraft/client/multiplayer/WorldClient") && ((MethodInsnNode) insnNode).desc.equals(ASMLoadingPlugin.isObf ? "(Lpk;Laug;Lcom/google/common/base/Predicate;)Ljava/util/List;" : "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;");
    }
}
