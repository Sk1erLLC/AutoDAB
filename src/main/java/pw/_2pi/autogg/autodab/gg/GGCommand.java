package pw._2pi.autogg.autodab.gg;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GGCommand extends CommandBase {
    public String getCommandName() {
        return "autodab";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public boolean canSenderUseCommand(final ICommandSender sender) {
        return true;
    }

    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length == 0 || args.length > 2) {
            this.showSyntaxError(sender);
            return;
        }
        final String s = args[0];
        switch (s) {
            case "meme": {

                break;
            }
            case "toggle":
            case "t": {
                AutoGG.getInstance().setToggled();
                this.showMessage(EnumChatFormatting.GRAY + "AutoDAB: " + (AutoGG.getInstance().isToggled() ? (EnumChatFormatting.GREEN + "On") : (EnumChatFormatting.RED + "Off")), sender);
                AutoGG.getInstance().getUtil().save();
                break;
            }
            case "f5":
            case "perspective": {
                AutoGG.getInstance().setF5(!AutoGG.getInstance().isF5());
                this.showMessage(EnumChatFormatting.GRAY + "AutoDAB: Set Auto F5 to " + (AutoGG.getInstance().isF5() ? (EnumChatFormatting.GREEN + "On") : (EnumChatFormatting.RED + "Off")), sender);
                AutoGG.getInstance().getUtil().save();
                break;
            }

            case "length":
            case "time": {
                if (args.length == 2) {
                    try {
                        final int length = Integer.parseInt(args[1]);
                        if (length < 0 || length > 5) {
                            throw new NumberFormatException("Invalid integer");
                        }
                        AutoGG.getInstance().setLength(length);
                        AutoGG.getInstance().getUtil().save();
                        this.showMessage(EnumChatFormatting.GRAY + "AutoDAB length set to " + EnumChatFormatting.GREEN + AutoGG.getInstance().getLength() + "s", sender);
                    } catch (NumberFormatException e) {
                        this.showError("Please use an integer between 1 and 5 seconds.", sender);
                    }
                    break;
                }
                this.showMessage(EnumChatFormatting.GRAY + "AutoDAB length: " + EnumChatFormatting.GREEN + AutoGG.getInstance().getLength() + "s", sender);
                break;
            }
            default: {
                this.showSyntaxError(sender);
                break;
            }
        }
    }

    public String getCommandUsage(final ICommandSender sender) {
        return "/autodab <toggle, length [seconds], f5>";
    }

    private void showMessage(final String message, final ICommandSender sender) {
        sender.addChatMessage((IChatComponent) new ChatComponentText(message));
    }

    private void showSyntaxError(final ICommandSender sender) {
        this.showMessage(EnumChatFormatting.RED + "Usage: " + this.getCommandUsage(sender), sender);
    }

    private void showError(final String error, final ICommandSender sender) {
        this.showMessage(EnumChatFormatting.RED + "Error: " + error, sender);
    }
}
