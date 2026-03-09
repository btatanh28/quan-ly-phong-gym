import { CommonModule, isPlatformBrowser } from '@angular/common';
import {
  Component,
  HostListener,
  Inject,
  OnInit,
  PLATFORM_ID,
} from '@angular/core';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { FormModule } from '../../../common/module/forms.module';
import { AuthService } from '../../../common/shared/service/application/authService';
import { DonHangService } from '../../../common/shared/service/application/donhangService';
import { ProductService } from '../../../common/shared/service/application/productService';

declare var bootstrap: any;

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, FormModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  public isScrolled = false;
  public showSearch = false;
  public currentUser: { name: string; role: string } | null = null;
  public donhang: any[] = [];
  public isModalOpen: boolean = false;
  // public orders: DonHang[] = [];
  public searchKeyword = '';
  public hasNewNotification: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private donhangService: DonHangService,
    private productService: ProductService,
    @Inject(PLATFORM_ID) private platformId: Object,
  ) {
    if (isPlatformBrowser(this.platformId)) {
      this.router.events.subscribe((event) => {
        if (event instanceof NavigationEnd) {
          const navbar = document.querySelector('#navbarNav');
          if (navbar && navbar.classList.contains('show')) {
            const bsCollapse = new bootstrap.Collapse(navbar, {
              toggle: false,
            });
            bsCollapse.hide();
          }
        }
      });
    }
  }

  async ngOnInit() {
    await this.getData();
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.isScrolled = window.pageYOffset > 50;
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  isEmployee(): boolean {
    return this.authService.isEmployee();
  }

  async getData() {
    this.authService.currentUserSubject$.subscribe((user) => {
      this.currentUser = user ? { name: user.name, role: user.role } : null;
      if (user && user.id) {
        this.donhangService.getDonHangById(user.id);
      }
    });
  }

  toggleSearch() {
    this.showSearch = !this.showSearch;
  }

  logout() {
    this.authService.logOut();
    this.donhang = [];
    this.router.navigate(['/login']);
  }

  closeModal() {
    this.isModalOpen = false;
  }
}
