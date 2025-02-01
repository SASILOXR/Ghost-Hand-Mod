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
        boolean findTransform = transformedName.equals("net.minecraft.client.renderer.entity.layers.LayerArmorBase");
        if (findTransform) {
            System.out.println("find!!!!!!!!!!!!!!!!!!");
            return transformClass(basicClass);
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
                    System.out.println(isFindNode(insNode));
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
                        System.out.println("asm success!!!!!!!!!!!!!!!!");


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

    public Boolean isFindNode(AbstractInsnNode insnNode) {
        if (insnNode instanceof MethodInsnNode) {
            System.out.println(((MethodInsnNode) insnNode).name);
            System.out.println(((MethodInsnNode) insnNode).owner);
            System.out.println(((MethodInsnNode) insnNode).desc);
        }
        return insnNode instanceof MethodInsnNode && insnNode.getOpcode() == INVOKEVIRTUAL && ((MethodInsnNode) insnNode).name.equals(ASMLoadingPlugin.isObf ? "a" : "getCurrentArmor") && ((MethodInsnNode) insnNode).owner.equals(ASMLoadingPlugin.isObf ? "bkn" : "net/minecraft/client/renderer/entity/layers/LayerArmorBase") && ((MethodInsnNode) insnNode).desc.equals(ASMLoadingPlugin.isObf ? "(Lpr;I)Lzx;" : "(Lnet/minecraft/entity/EntityLivingBase;I)Lnet/minecraft/item/ItemStack;");

    }
}
