import { execSync } from "child_process";

// 通过包名得到包的根路径
function packagePath(packageName) {
  let path;
  try {
    const stdout = execSync("pnpm list --recursive --depth -1 --json");
    const json = JSON.parse(stdout.toString());

    if (packageName) {
      path = json.find((item) => item.name === packageName).path;
    }
    return path.replaceAll("\\", `/`);
  } catch (error) {
    console.error(`执行命令时出错: ${error}`);
    return null;
  }
}
export default packagePath;
