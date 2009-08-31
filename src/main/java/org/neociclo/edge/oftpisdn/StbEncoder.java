/**
 * Neociclo Accord, Open Source B2Bi Middleware
 * Copyright (C) 2005-2009 Neociclo, http://www.neociclo.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id$
 */
package org.neociclo.edge.oftpisdn;

import static org.jboss.netty.buffer.ChannelBuffers.*;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * @author Rafael Marins
 * @version $Rev$ $Date$
 */
@ChannelPipelineCoverage("all")
public class StbEncoder extends OneToOneEncoder {

    private static final int STB_HEADER_SIZE = 4;

    private static final byte STB_V1_NOFLAGS_HEADER1 = (byte) 0x10;

    @Override
    protected Object encode(
            ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {

        ChannelBuffer header = channel.getConfig().getBufferFactory().getBuffer(ByteOrder.BIG_ENDIAN, STB_HEADER_SIZE);
        ChannelBuffer body = (ChannelBuffer) msg;

        int length = STB_HEADER_SIZE + body.readableBytes();
        header.writeByte(STB_V1_NOFLAGS_HEADER1);
        header.writeMedium(length);

        return wrappedBuffer(header, body);
    }

}
