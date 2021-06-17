package nextstep.subway.favorite.ui;

import nextstep.subway.auth.domain.AuthenticationPrincipal;
import nextstep.subway.auth.domain.LoginMember;
import nextstep.subway.favorite.application.FavoriteService;
import nextstep.subway.favorite.dto.FavoriteRequest;
import nextstep.subway.favorite.dto.FavoriteResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class FavoriteController {

  private final FavoriteService favoriteService;

  public FavoriteController(FavoriteService favoriteService) {
    this.favoriteService = favoriteService;
  }

  @PostMapping(value = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FavoriteResponse> createMember(@AuthenticationPrincipal LoginMember loginMember, @RequestBody FavoriteRequest request) {
    FavoriteResponse favoriteResponse = favoriteService.saveFavorite(loginMember.getId(), request);
    return ResponseEntity.created(URI.create("/favorites/" + favoriteResponse.getId())).body(favoriteResponse);
  }

  @GetMapping("favorites")
  public ResponseEntity<List<FavoriteResponse>> findFavorites(@AuthenticationPrincipal LoginMember loginMember) {
    return ResponseEntity.ok(favoriteService.findFavorites(loginMember.getId()));
  }
}
