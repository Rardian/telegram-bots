package de.rardian.telegram.bot.command;


public class CommandUnknownAction implements Action, SendsAnswer {

	private MessageReply reply;

	@Override
	public void setMessageReply(MessageReply reply) {
		this.reply = reply;
	}

	@Override
	public void execute() {
		reply.answer("Kommando unbekannt", null);
	}

}
