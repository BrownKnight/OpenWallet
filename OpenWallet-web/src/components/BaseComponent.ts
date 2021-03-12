import { Vue } from "vue-property-decorator";
import moment from "moment";
import { DataApi } from "@/data/DataApi";

export class BaseComponent extends Vue {
  dataApi: DataApi = new DataApi(this.showMessage.bind(this));

  showMessage({ message, variant, delay }: { message: string; variant?: string; delay?: number }) {
    this.$bvToast.toast(message, {
      noCloseButton: true,
      variant: variant ?? "success",
      autoHideDelay: delay ?? 3000,
      toaster: "b-toaster-top-right",
      href: "#"
    });
  }

  isAdmin(): boolean {
    return false;
  }

  get isLoggedIn(): boolean {
    return this.$store.state?.AuthModule?.token !== null;
  }

  prettyFormatDate(date: string | Date): string {
    return moment(date).format("ddd, Do MMM");
  }

  prettyFormatTime(time: string | Date): string {
    return moment(time, "HH:mm").format("HH:mm");
  }

  prettyFormatDateTime(datetime: string | Date): string {
    return moment(datetime).format("DD Oct, HH:mm");
  }
}
