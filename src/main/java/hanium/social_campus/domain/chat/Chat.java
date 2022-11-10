package hanium.social_campus.domain.chat;

import hanium.social_campus.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    private String sender;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    public void addChat(ChatRoom chatRoom){
        chatRoom.getChats().add(this);
        this.chatRoom = chatRoom;
    }

    public static Chat create(ChatRoom chatRoom, String sender, String message){
        Chat chat = new Chat();
        chat.addChat(chatRoom);
        chat.sender = sender;
        chat.message = message;

        return chat;
    }

}
