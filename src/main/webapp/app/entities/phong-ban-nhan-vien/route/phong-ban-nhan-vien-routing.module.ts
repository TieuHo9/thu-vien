import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PhongBanNhanVienComponent } from '../list/phong-ban-nhan-vien.component';
import { PhongBanNhanVienDetailComponent } from '../detail/phong-ban-nhan-vien-detail.component';
import { PhongBanNhanVienUpdateComponent } from '../update/phong-ban-nhan-vien-update.component';
import { PhongBanNhanVienRoutingResolveService } from './phong-ban-nhan-vien-routing-resolve.service';

const phongBanNhanVienRoute: Routes = [
  {
    path: '',
    component: PhongBanNhanVienComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhongBanNhanVienDetailComponent,
    resolve: {
      phongBanNhanVien: PhongBanNhanVienRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhongBanNhanVienUpdateComponent,
    resolve: {
      phongBanNhanVien: PhongBanNhanVienRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhongBanNhanVienUpdateComponent,
    resolve: {
      phongBanNhanVien: PhongBanNhanVienRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(phongBanNhanVienRoute)],
  exports: [RouterModule],
})
export class PhongBanNhanVienRoutingModule {}
