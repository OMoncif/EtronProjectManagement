import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { BorneComponent } from '../dialog/borne/borne.component';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EtronService } from 'src/app/services/etron.service';

@Component({
  selector: 'app-lister-borne',
  templateUrl: './lister-borne.component.html',
  styleUrls: ['./lister-borne.component.scss']
})
export class ListerBorneComponent {
  locationForm: FormGroup;
  displayedColumns: string[] = [ 'typecharge', 'latitude', 'longitude', 'disponible', 'actions'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);

  nearbyChargingStations: any;

  constructor(private snackBar: MatSnackBar, private etronService: EtronService) {
    this.locationForm = new FormGroup({
      userLatitude: new FormControl(null, [Validators.required]),
      userLongitude: new FormControl(null, [Validators.required]),
    });
  }

  ListNearbyBornes() {
    if (this.locationForm.valid) {
      const userLatitude = this.locationForm.get('userLatitude')?.value;
      const userLongitude = this.locationForm.get('userLongitude')?.value;

      if (userLatitude !== null && userLongitude !== null) {
        const requestData = {
          userLatitude,
          userLongitude,
        };

        this.etronService.listNearbyBornes(requestData).subscribe(
          (response: any) => {
            this.nearbyChargingStations = response;
            this.dataSource.data = this.nearbyChargingStations;
          },
          (error: any) => {
            console.error('Error fetching nearby bornes:', error);
            this.snackBar.open('An error occurred while fetching nearby bornes.', 'Close', {
              duration: 3000,
            });
          }
        );
      }
    } else {
      this.snackBar.open('Please enter valid latitude and longitude.', 'Close', {
        duration: 3000,
      });
    }
  }
}
