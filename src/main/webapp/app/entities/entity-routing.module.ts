import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'chuyen-cong-tac',
        data: { pageTitle: 'ChuyenCongTacs' },
        loadChildren: () => import('./chuyen-cong-tac/chuyen-cong-tac.module').then(m => m.ChuyenCongTacModule),
      },
      {
        path: 'nhan-vien',
        data: { pageTitle: 'NhanViens' },
        loadChildren: () => import('./nhan-vien/nhan-vien.module').then(m => m.NhanVienModule),
      },
      {
        path: 'nhan-vien-cong-tac',
        data: { pageTitle: 'NhanVienCongTacs' },
        loadChildren: () => import('./nhan-vien-cong-tac/nhan-vien-cong-tac.module').then(m => m.NhanVienCongTacModule),
      },
      {
        path: 'phong-ban-nhan-vien',
        data: { pageTitle: 'PhongBanNhanViens' },
        loadChildren: () => import('./phong-ban-nhan-vien/phong-ban-nhan-vien.module').then(m => m.PhongBanNhanVienModule),
      },
      {
        path: 'phong-ban',
        data: { pageTitle: 'PhongBans' },
        loadChildren: () => import('./phong-ban/phong-ban.module').then(m => m.PhongBanModule),
      },
      {
        path: 'chi-phi',
        data: { pageTitle: 'ChiPhis' },
        loadChildren: () => import('./chi-phi/chi-phi.module').then(m => m.ChiPhiModule),
      },
      {
        path: 'dinh-muc',
        data: { pageTitle: 'DinhMucs' },
        loadChildren: () => import('./dinh-muc/dinh-muc.module').then(m => m.DinhMucModule),
      },
      {
        path: 'de-xuat-thanh-toan',
        data: { pageTitle: 'DeXuatThanhToans' },
        loadChildren: () => import('./de-xuat-thanh-toan/de-xuat-thanh-toan.module').then(m => m.DeXuatThanhToanModule),
      },
      {
        path: 'cap-bac',
        data: { pageTitle: 'CapBacs' },
        loadChildren: () => import('./cap-bac/cap-bac.module').then(m => m.CapBacModule),
      },
      {
        path: 'xet-duyet',
        data: { pageTitle: 'XetDuyet' },
        loadChildren: () => import('./xet-duyet/xet-duyet.module').then(m => m.XetDuyetModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
