import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { PhongBanComponent } from './list/phong-ban.component';
import { PhongBanDetailComponent } from './detail/phong-ban-detail.component';
import { PhongBanUpdateComponent } from './update/phong-ban-update.component';
import { PhongBanDeleteDialogComponent } from './delete/phong-ban-delete-dialog.component';
import { PhongBanRoutingModule } from './route/phong-ban-routing.module';

@NgModule({
  imports: [SharedModule, PhongBanRoutingModule],
  declarations: [PhongBanComponent, PhongBanDetailComponent, PhongBanUpdateComponent, PhongBanDeleteDialogComponent],
  entryComponents: [PhongBanDeleteDialogComponent],
})
export class PhongBanModule {}
