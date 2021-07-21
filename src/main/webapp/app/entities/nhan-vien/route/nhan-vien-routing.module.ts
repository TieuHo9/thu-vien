import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NhanVienComponent } from '../list/nhan-vien.component';
import { NhanVienDetailComponent } from '../detail/nhan-vien-detail.component';
import { NhanVienUpdateComponent } from '../update/nhan-vien-update.component';
import { NhanVienRoutingResolveService } from './nhan-vien-routing-resolve.service';

const nhanVienRoute: Routes = [
  {
    path: '',
    component: NhanVienComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NhanVienDetailComponent,
    resolve: {
      nhanVien: NhanVienRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NhanVienUpdateComponent,
    resolve: {
      nhanVien: NhanVienRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NhanVienUpdateComponent,
    resolve: {
      nhanVien: NhanVienRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nhanVienRoute)],
  exports: [RouterModule],
})
export class NhanVienRoutingModule {}
