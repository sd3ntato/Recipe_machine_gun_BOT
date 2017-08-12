import telepot
from pprint import pprint

Statomacchina = 0


def handle(msg):
	content_type, chat_type, chat_id = telepot.glance(msg)

	global Statomacchina


	chat_id = msg['chat']['id']
	command_input = msg['text']

	print(chat_id, content_type, chat_type)

	response = bot.getUpdates()
	print "msg:"
	pprint(msg)
	if Statomacchina == 0:

			bot.sendMessage(chat_id, risposta)
			Statomacchina = 1
	else:
			bot.sendMessage(chat_id, "risposta")


bot = telepot.Bot('token')

bot.message_loop(handle)

print("ASCOLTO LA VOSTRA VOCE")

while 1:
	time.sleep(10)
