import { AuthService } from './../../../../common/shared/service/application/authService';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ChatBotService } from '../../../../common/shared/service/application/chatbotService';

@Component({
  selector: 'app-chat-bot',
  imports: [FormModule, CommonModule],
  templateUrl: './chat-bot.component.html',
  styleUrls: ['./chat-bot.component.css'],
})
export class ChatBotComponent implements OnInit {
  @ViewChild('chatBody') chatBody!: ElementRef;

  public formChat!: FormGroup;
  public hideChat = false;

  public messages: {
    sender: string;
    content: string;
  }[] = [
    {
      sender: 'bot',
      content: 'Xin chào! Mình có thể giúp gì cho bạn hôm nay?',
    },
  ];

  constructor(
    private fb: FormBuilder,
    private chatBotService: ChatBotService,
    private authService: AuthService,
  ) {
    this.formChat = this.fb.group({
      message: '',
    });
  }

  ngOnInit() {
    this.hideChat = true;
  }

  scrollToBottom() {
    setTimeout(() => {
      const element = this.chatBody.nativeElement;

      element.scrollTop = element.scrollHeight;
    }, 100);
  }

  sendMessage() {
    const message = this.formChat.get('message')?.value;

    if (!message || !message.trim()) {
      return;
    }

    // thêm tin nhắn người dùng
    this.messages.push({
      sender: 'user',

      content: message,
    });

    this.scrollToBottom();

    this.formChat.reset();

    const request = {
      id: this.authService.getUserCurrent()?.id,

      message: message,
    };

    this.chatBotService.ChatBot(request).subscribe({
      next: (res) => {
        this.messages.push({
          sender: 'bot',

          content: res,
        });
        this.scrollToBottom();
      },

      error: (err) => {
        console.error(err);

        this.messages.push({
          sender: 'bot',

          content: 'Xin lỗi, hiện tại chatbot không thể trả lời.',
        });
      },
    });
  }
}
