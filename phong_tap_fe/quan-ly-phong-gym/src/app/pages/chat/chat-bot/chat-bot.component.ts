import { AuthService } from './../../../../common/shared/service/application/authService';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ChatBotService } from '../../../../common/shared/service/application/chatbotService';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-chat-bot',
  imports: [FormModule, CommonModule, RouterModule],
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
    showBuyButton?: boolean;
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
    private router: Router,
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

  // Kiểm tra câu hỏi có liên quan đến sản phẩm không
  isProductQuestion(message: string): boolean {
    const lower = message.toLowerCase();

    const keywords = [
      'sản phẩm',
      'bình nước',
      'whey',
      'protein',
      'găng tay',
      'thảm tập',
      'bó gối',
      'yến mạch',
      'dây kháng lực',
      'áo thun',
      'dây nhảy',
      'creatine',
      'kìm bóp tay',
      'shaker',
      'mua hàng',
      'mua sản phẩm',
    ];

    return keywords.some((keyword) => lower.includes(keyword));
  }

  // Chuyển sang trang menu và đến danh sách sản phẩm
  goToProducts() {
    this.hideChat = true;

    this.router.navigate(['/menu'], {
      fragment: 'san-pham',
    });
  }

  sendMessage() {
    const message = this.formChat.get('message')?.value?.trim();

    if (!message) return;

    this.messages.push({
      sender: 'user',
      content: message,
    });

    this.scrollToBottom();
    this.formChat.reset();

    const currentUser = this.authService.getUserCurrent();

    const request = {
      id: currentUser?.id ?? null,
      message: message,
    };

    this.chatBotService.ChatBot(request).subscribe({
      next: (res) => {
        this.messages.push({
          sender: 'bot',
          content: res,
          showBuyButton: this.isProductQuestion(message),
        });
        this.scrollToBottom();
      },
      error: (err) => {
        console.error(err);

        this.messages.push({
          sender: 'bot',
          content: 'Xin lỗi, hiện tại chatbot không thể trả lời.',
        });

        this.scrollToBottom();
      },
    });
  }
}
