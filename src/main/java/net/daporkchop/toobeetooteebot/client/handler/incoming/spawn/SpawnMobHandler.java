/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2016-2019 DaPorkchop_
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.toobeetooteebot.client.handler.incoming.spawn;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnMobPacket;
import lombok.NonNull;
import net.daporkchop.toobeetooteebot.client.PorkClientSession;
import net.daporkchop.toobeetooteebot.util.cache.data.entity.EntityMob;
import net.daporkchop.toobeetooteebot.util.handler.HandlerRegistry;

import java.util.Arrays;

/**
 * @author DaPorkchop_
 */
public class SpawnMobHandler implements HandlerRegistry.IncomingHandler<ServerSpawnMobPacket, PorkClientSession> {
    @Override
    public boolean apply(@NonNull ServerSpawnMobPacket packet, @NonNull PorkClientSession session) {
        CACHE.getEntityCache().add(new EntityMob()
                .setMobType(packet.getType())
                .setEntityId(packet.getEntityId())
                .setUuid(packet.getUUID())
                .setX(packet.getX())
                .setY(packet.getY())
                .setZ(packet.getZ())
                .setYaw(packet.getYaw())
                .setPitch(packet.getPitch())
                .setHeadYaw(packet.getHeadYaw())
                .setVelX(packet.getMotionX())
                .setVelY(packet.getMotionY())
                .setVelZ(packet.getMotionZ())
                .setMetadata(Arrays.asList(packet.getMetadata()))
        );
        return true;
    }

    @Override
    public Class<ServerSpawnMobPacket> getPacketClass() {
        return ServerSpawnMobPacket.class;
    }
}
