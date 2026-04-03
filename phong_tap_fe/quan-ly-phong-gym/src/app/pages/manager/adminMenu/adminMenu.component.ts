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

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.user = this.authService.getUserCurrent();
  }

  setTab(tab: string) {
    this.currentTab = tab;
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

  logout() {
    this.authService.logOut();
    this.router.navigate(['/login']);
  }
}
