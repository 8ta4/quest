{ pkgs, lib, config, inputs, ... }:

{
  # https://devenv.sh/basics/
  env.GREET = "devenv";

  # https://devenv.sh/packages/
  packages = [
    pkgs.git
    pkgs.gitleaks
  ];

  # https://devenv.sh/scripts/
  scripts.hello.exec = "echo hello from $GREET";
  scripts.run.exec = ''
    brew bundle
    # The default Content Security Policy (CSP) for web extensions is:
    # "script-src 'self' 'wasm-unsafe-eval';"
    # However, I encountered the following error:
    # EvalError: call to eval() blocked by CSP
    # To resolve this, I added 'unsafe-eval' to the CSP to allow eval() calls.
    web-ext run --pref extensions.webextensions.base-content-security-policy.v3="script-src 'self' 'wasm-unsafe-eval' 'unsafe-eval';" -s public --start-url localhost:8000?quest=localhost:8000/index.yaml
  '';

  enterShell = ''
    hello
    git --version
    export PATH="$DEVENV_ROOT/node_modules/.bin:$PATH"
    npm i
  '';

  # https://devenv.sh/tests/
  enterTest = ''
    echo "Running tests"
  '';

  # https://devenv.sh/services/
  # services.postgres.enable = true;

  # https://devenv.sh/languages/
  # languages.nix.enable = true;
  languages.clojure.enable = true;

  # https://devenv.sh/pre-commit-hooks/
  # pre-commit.hooks.shellcheck.enable = true;
  pre-commit.hooks = {
    cljfmt.enable = true;
    gitleaks = {
      enable = true;
      # https://github.com/gitleaks/gitleaks/blob/4e43d1109303568509596ef5ef576fbdc0509891/.pre-commit-hooks.yaml#L4
      entry = "gitleaks protect --verbose --redact --staged";
    };
    nixpkgs-fmt.enable = true;
    prettier.enable = true;
    # https://github.com/cachix/git-hooks.nix/issues/31#issuecomment-744657870
    trailing-whitespace = {
      enable = true;
      # https://github.com/pre-commit/pre-commit-hooks/blob/ed714747d7acbc5790b171702bb012af3b0fe145/.pre-commit-hooks.yaml#L203-L205
      entry = "${pkgs.python3Packages.pre-commit-hooks}/bin/trailing-whitespace-fixer";
      types = [ "text" ];
    };
  };

  # https://devenv.sh/processes/
  # processes.ping.exec = "ping example.com";

  # See full reference at https://devenv.sh/reference/options/
}
