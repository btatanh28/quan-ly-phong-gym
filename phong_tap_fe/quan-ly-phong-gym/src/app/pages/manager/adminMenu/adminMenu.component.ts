import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';

import { CommonModule } from '@angular/common';
import { FormModule } from '../../../../common/module/forms.module';
import { WelcomeComponent } from '../welcome/welcome.component';
import { AuthService } from '../../../../common/shared/service/application/authService';
import { AccountComponent } from '../account/account.component';
import { vaiTroOptions } from '../../../../common/shared/enums/vaiTro.enums';
import { LabelValuePipe } from '../../../../common/base/pipe/labelValue/labelValue.component';
import { PostComponent } from '../post/post.component';
import { ForumComponent } from '../forum/forum.component';
import { ProductComponent } from '../product/product.component';
import { OrderComponent } from '../order/order.component';
import { ScanCheckinComponent } from '../scan-checkin/scan-checkin.component';
import { ExerciseCardComponent } from '../exercise-card/exercise-card.component';
import { ContactManagerComponent } from '../contact-manager/contact-manager.component';
import { RevenueComponent } from '../revenue/revenue.component';
import { CoachComponent } from '../coach/coach.component';
import { ThongBaoService } from '../../../../common/shared/service/application/thongbaoService';
import { firstValueFrom, interval, Subscription } from 'rxjs';
import { DonHangService } from '../../../../common/shared/service/application/donhangService';

@Component({
  selector: 'app-adminMenu',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    WelcomeComponent,
    AccountComponent,
    LabelValuePipe,
    PostComponent,
    ForumComponent,
    ProductComponent,
    OrderComponent,
    ScanCheckinComponent,
    ExerciseCardComponent,
    ContactManagerComponent,
    RevenueComponent,
    CoachComponent,
  ],
  templateUrl: './adminMenu.component.html',
  styleUrls: ['./adminMenu.component.css'],
})
export class AdminMenuComponent implements OnInit {
  @Output() tabSelected = new EventEmitter<string>();

  public currentTab: string = 'welcome';
  public pageTitle: string = 'welcome';
  public isCollapsed = false;
  public user: any;
  public listVaiTro: any[] = vaiTroOptions;

  // Số thông báo đơn hàng
  public newOrderCount = 0;

  // Timer kiểm tra đơn hàng mỗi phút
  private notificationSubscription?: Subscription;

  // Subscription lắng nghe số lượng thông báo
  private countSubscription?: Subscription;

  constructor(
    private authService: AuthService,
    private router: Router,
    private thongBaoService: ThongBaoService,
    private donHangService: DonHangService,
  ) {}

  async ngOnInit() {
    this.user = this.authService.getUserCurrent();
    await this.checkNewOrders();

    this.countSubscription = this.thongBaoService.newOrderCount$.subscribe(
      (count) => {
        this.newOrderCount = count;
      },
    );

    this.notificationSubscription = interval(60 * 1000).subscribe(() => {
      this.checkNewOrders();
    });
  }

  private async checkNewOrders() {
    try {
      const res = await firstValueFrom(
        this.donHangService.GetAllDonHang({
          page: 0,
          size: 1000,
        }),
      );

      this.thongBaoService.updateNewOrderCount(res.items);
    } catch (error) {
      this.thongBaoService.clear();
    }
  }

  ngOnDestroy() {
    this.notificationSubscription?.unsubscribe();
    this.countSubscription?.unsubscribe();
  }

  setTab(tab: string) {
    this.currentTab = tab;

    if (tab === 'orders') {
      this.thongBaoService.clear();
    }

    this.pageTitle = this.getPageTitle();
  }

  getPageTitle(): string {
    switch (this.currentTab) {
      case 'welcome':
        return 'Welcome';
      case 'products':
        return 'Quản lý sản phẩm';
      case 'posts':
        return 'Quản lý bài viết';
      case 'orders':
        return 'Quản lý đơn hàng';
      case 'forums':
        return 'Quản lý diễn đàn';
      case 'statistics':
        return 'Thống kê';
      case 'users':
        return 'Quản lý người dùng';
      case 'card':
        return 'Thẻ tập';
      case 'scan-checkin':
        return 'Check In';
      case 'contact':
        return 'Liên hệ khách hàng';
      case 'revenue':
        return 'Doanh thu';
      case 'coach':
        return 'Huấn luyện viên';
      default:
        return 'users';
    }
  }

  isAdmin(): boolean {
    return this.user?.role === 1;
  }

  isEmployee(): boolean {
    return this.user?.role === 4;
  }

  isCoach(): boolean {
    return this.user?.role === 5;
  }

  logout() {
    this.authService.logOut();
    this.router.navigate(['/login']);
  }
}
