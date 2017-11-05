/*
 This file is part of the OdinMS Maple Story Server
 and the Maple83 MapleStory Server
 Copyright (C) 2017 Patrick Huy <patrick.huy@frz.cc>
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>
 Jonathan Lin <jlin3@scu.edu>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as
 published by the Free Software Foundation version 3 as published by
 the Free Software Foundation. You may not use, modify or distribute
 this program under any other version of the GNU Affero General Public
 License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.server.channel.handlers;

import client.MapleCharacter;
import client.MapleClient;
import net.AbstractMaplePacketHandler;
import net.SendOpcode;
import net.server.Server;
import net.server.guild.MapleGuild;
import net.server.guild.MapleGuildCharacter;
import net.server.guild.MapleAlliance;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.data.output.MaplePacketLittleEndianWriter;

/**
 *
 * @author XoticStory
 */
public final class AllianceOperationHandler extends AbstractMaplePacketHandler {

    @Override
    public final void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        MapleAlliance alliance = null;
        if (c.getPlayer().getGuild() != null && c.getPlayer().getGuild().getAllianceId() > 0) {
            alliance = c.getPlayer().getAlliance();
        }
        if (alliance == null) {
            c.announce(MaplePacketCreator.enableActions());
            return;
        } else if (c.getPlayer().getMGC().getAllianceRank() > 2 || !alliance.getGuilds().contains(c.getPlayer().getGuildId())) {
            c.announce(MaplePacketCreator.enableActions());
            return;
        }
        
        byte b = slea.readByte();
        switch (b) {
            case 0x01:
                Server.getInstance().allianceMessage(alliance.getId(), sendShowInfo(c.getPlayer().getGuild().getAllianceId(), c.getPlayer().getId()), -1, -1);
                break;
            case 0x02: { // Leave Alliance
                if (c.getPlayer().getGuild().getAllianceId() == 0 || c.getPlayer().getGuildId() < 1 || c.getPlayer().getGuildRank() != 1) {
                    return;
                }
                
                MapleAlliance.removeGuildFromAlliance(c.getPlayer().getGuild().getAllianceId(), c.getPlayer().getGuildId(), c.getPlayer().getWorld());
                break;
            }
            case 0x03: // send alliance invite... or at least it would be this way if i could find the right way to call it!
                String guildName = slea.readMapleAsciiString();
                
                if(alliance.getGuilds().size() == alliance.getCapacity()) {
                    c.getPlayer().dropMessage("Your alliance can not comport any more guild at the moment.");
                } else {
                    MapleGuild mg = Server.getInstance().getGuildByName(guildName);
                    if(mg == null) {
                        c.getPlayer().dropMessage("The entered guild does not exist.");
                    }
                    else {
                        MapleCharacter victim = mg.getMGC(mg.getLeaderId()).getCharacter();
                        
                        if (victim == null) {
                            c.getPlayer().dropMessage("The master of the guild that you offered an invitation is currently not online.");
                        } else {
                            // this doesn't seem to work...
                            //Server.getInstance().allianceMessage(alliance.getId(), sendInvitation(c.getPlayer().getGuild().getAllianceId(), victim.getId(), guildName), -1, -1);
                            //victim.getClient().announce(sendInvitation(c.getPlayer().getGuild().getAllianceId(), c.getPlayer().getId(), guildName));
                            
                            if(!c.getPlayer().isPartyMember(victim)) {
                                c.getPlayer().dropMessage("The master of the guild that you offered a invitation must be in the same party as yours.");
                            }
                            else {
                                int guildid = victim.getGuildId();

                                Server.getInstance().addGuildtoAlliance(alliance.getId(), guildid);
                                Server.getInstance().resetAllianceGuildPlayersRank(guildid);

                                Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.addGuildToAlliance(alliance, guildid, c), -1, -1);
                                Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.updateAllianceInfo(alliance, c), -1, -1);
                                Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.allianceNotice(alliance.getId(), alliance.getNotice()), -1, -1);
                                victim.getGuild().dropMessage("Your guild has joined the [" + alliance.getName() + "] union.");
                            }
                        }
                    }
                }
                
                break;
            case 0x04: {
                int guildid = slea.readInt();
//                    slea.readMapleAsciiString();//guild name
                if (c.getPlayer().getGuild().getAllianceId() != 0 || c.getPlayer().getGuildRank() != 1 || c.getPlayer().getGuildId() < 1) {
                    return;
                }
                Server.getInstance().allianceMessage(alliance.getId(), sendChangeGuild(guildid, c.getPlayer().getId(), c.getPlayer().getGuildId(), 0), -1, -1);
                break;
            }
            case 0x06: { // Expel Guild
                int guildid = slea.readInt();
                int allianceid = slea.readInt();
                if (c.getPlayer().getGuild().getAllianceId() == 0 || c.getPlayer().getGuild().getAllianceId() != allianceid) {
                    return;
                }
                
                Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.removeGuildFromAlliance(alliance, guildid, c.getWorld()), -1, -1);
                Server.getInstance().removeGuildFromAlliance(alliance.getId(), guildid);
                
                Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.getGuildAlliances(alliance, c.getWorld()), -1, -1);
                Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.allianceNotice(alliance.getId(), alliance.getNotice()), -1, -1);
                Server.getInstance().guildMessage(guildid, MaplePacketCreator.disbandAlliance(allianceid));
                
                alliance.dropMessage("[" + Server.getInstance().getGuild(guildid).getName() + "] guild has been expelled from the union.");
                break;
            }
            case 0x07: { // Change Alliance Leader
                if (c.getPlayer().getGuild().getAllianceId() == 0 || c.getPlayer().getGuildId() < 1) {
                    return;
                }
                int victimid = slea.readInt();
                MapleCharacter player = Server.getInstance().getWorld(c.getWorld()).getPlayerStorage().getCharacterById(victimid);
                
                //Server.getInstance().allianceMessage(alliance.getId(), sendChangeLeader(c.getPlayer().getGuild().getAllianceId(), c.getPlayer().getId(), slea.readInt()), -1, -1);
                changeLeaderAllianceRank(alliance, player);
                break;
            }
            case 0x08:
                String ranks[] = new String[5];
                for (int i = 0; i < 5; i++) {
                    ranks[i] = slea.readMapleAsciiString();
                }
                Server.getInstance().setAllianceRanks(alliance.getId(), ranks);
                Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.changeAllianceRankTitle(alliance.getId(), ranks), -1, -1);
                break;
            case 0x09: {
                int int1 = slea.readInt();
                byte byte1 = slea.readByte();
                
                //Server.getInstance().allianceMessage(alliance.getId(), sendChangeRank(c.getPlayer().getGuild().getAllianceId(), c.getPlayer().getId(), int1, byte1), -1, -1);
                MapleCharacter player = Server.getInstance().getWorld(c.getWorld()).getPlayerStorage().getCharacterById(int1);
                changePlayerAllianceRank(alliance, player, (byte1 > 0));
                
                break;
            }
            case 0x0A:
                String notice = slea.readMapleAsciiString();
                Server.getInstance().setAllianceNotice(alliance.getId(), notice);
                Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.allianceNotice(alliance.getId(), notice), -1, -1);
                
                alliance.dropMessage(5, "* Alliance Notice : " + notice);
                break;
            default:
                c.getPlayer().dropMessage("Feature not available");
        }
        
        alliance.saveToDB();
    }
    
    private void changeLeaderAllianceRank(MapleAlliance alliance, MapleCharacter newLeader) {
        MapleGuildCharacter lmgc = alliance.getLeader();
        MapleCharacter leader = Server.getInstance().getWorld(newLeader.getWorld()).getPlayerStorage().getCharacterById(lmgc.getId());
        leader.getMGC().setAllianceRank(2);
        leader.saveGuildStatus();
        
        newLeader.getMGC().setAllianceRank(1);
        newLeader.saveGuildStatus();
        
        Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.getGuildAlliances(alliance, newLeader.getWorld()), -1, -1);
        alliance.dropMessage("'" + newLeader.getName() + "' has been appointed as the new head of this Alliance.");
    }
    
    private void changePlayerAllianceRank(MapleAlliance alliance, MapleCharacter chr, boolean raise) {
        int newRank = chr.getAllianceRank() + (raise ? -1 : 1);
        if(newRank < 3 || newRank > 5) return;
        
        chr.getMGC().setAllianceRank(newRank);
        chr.saveGuildStatus();
        
        Server.getInstance().allianceMessage(alliance.getId(), MaplePacketCreator.getGuildAlliances(alliance, chr.getWorld()), -1, -1);
        alliance.dropMessage("'" + chr.getName() + "' has been reassigned as '" + alliance.getRankTitle(newRank) + "' in this Alliance.");
    }

    private static byte[] sendShowInfo(int allianceid, int playerid) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendOpcode.ALLIANCE_OPERATION.getValue());
        mplew.write(0x02);
        mplew.writeInt(allianceid);
        mplew.writeInt(playerid);
        return mplew.getPacket();
    }

    @SuppressWarnings("unused")
	private static byte[] sendInvitation(int allianceid, int playerid, final String guildname) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendOpcode.ALLIANCE_OPERATION.getValue());
        mplew.write(0x05);
        mplew.writeInt(allianceid);
        mplew.writeInt(playerid);
        mplew.writeMapleAsciiString(guildname);
        return mplew.getPacket();
    }

    private static byte[] sendChangeGuild(int allianceid, int playerid, int guildid, int option) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendOpcode.ALLIANCE_OPERATION.getValue());
        mplew.write(0x07);
        mplew.writeInt(allianceid);
        mplew.writeInt(guildid);
        mplew.writeInt(playerid);
        mplew.write(option);
        return mplew.getPacket();
    }

    @SuppressWarnings("unused")
	private static byte[] sendChangeLeader(int allianceid, int playerid, int victim) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendOpcode.ALLIANCE_OPERATION.getValue());
        mplew.write(0x08);
        mplew.writeInt(allianceid);
        mplew.writeInt(playerid);
        mplew.writeInt(victim);
        return mplew.getPacket();
    }

    @SuppressWarnings("unused")
	private static byte[] sendChangeRank(int allianceid, int playerid, int int1, byte byte1) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendOpcode.ALLIANCE_OPERATION.getValue());
        mplew.write(0x09);
        mplew.writeInt(allianceid);
        mplew.writeInt(playerid);
        mplew.writeInt(int1);
        mplew.writeInt(byte1);
        return mplew.getPacket();
    }
}
